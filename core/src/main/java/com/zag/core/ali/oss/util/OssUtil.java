package com.zag.core.ali.oss.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.*;
import com.aliyun.oss.model.LifecycleRule.RuleStatus;
import com.aliyun.oss.model.SetBucketCORSRequest.CORSRule;
import com.zag.core.ali.oss.config.OssConfig;
import com.zag.core.exception.BusinessException;
import com.zag.core.exception.FunctionExceptions;
import com.zag.core.exception.RrbSystemException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.activation.MimetypesFileTypeMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stone
 * @date 2017年8月16日
 * @reviewer
 */
public class OssUtil {
    private static Logger logger = LoggerFactory.getLogger(OssUtil.class);

    public static final String bucketName = OssConfig.defaultBucketName;

    /**
     * 实例化外网oss客户端
     * @return
     * @throws RrbSystemException
     */
    private static OSSClient getOSSClient() throws RrbSystemException {
        OSSClient client = null;
        try {
            client = new OSSClient(OssConfig.endpoint, OssConfig.accessKeyId, OssConfig.accessKeySecret);
        } catch (Exception e) {
            logger.error("实例化oss外网客户端错误\n");
            throw new RrbSystemException(e);
        }
        return client;
    }

    /**
     * oss服务端异常公共方法
     * @param oe
     * @throws RrbSystemException
     */
    private static void OSSExceptionLoggerPrint(OSSException oe) throws RrbSystemException {
        logger.error("oss服务端异常...\n");
        logger.error("errorCode:\n", oe.getErrorCode());
        logger.error("errorMsg:\n", oe.getErrorMessage());
        logger.error("requestId:\n", oe.getRequestId());
        logger.error("hostId:\n", oe.getHostId());
        throw new RrbSystemException(oe.getErrorMessage(), oe);
    }
    /**
     * oss客户端异常处理公共方法
     * @param ce
     * @throws RrbSystemException
     */
    private static void clientExceptionLoggerPrint(ClientException ce) throws RrbSystemException {
        logger.error("oss客户端异常...\n");
        logger.error("errorCode:\n", ce.getErrorCode());
        logger.error("errorMsg:\n", ce.getErrorMessage());
        throw new RrbSystemException(ce.getErrorMessage(), ce);
    }
    /**
     * 解析ISO8601格式的字符串为一个时间对象
     * @param dateString
     * @return
     */
    private static Date parseISO8601Date(String dateString) {
        final String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 格式化时间格式为ISO8601格式
     * @param date
     * @return
     */
    private static String formatISO8601Date(Date date) {
        final String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return dateFormat.format(date);
    }
    /**
     * 获取上传进度回调
     *
     */
    private static class PutObjectProgressListener implements ProgressListener {

        private long bytesWritten = 0;
        private long totalBytes = -1;
        private boolean succeed = false;

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to upload......");
                    break;

                case REQUEST_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                    break;

                case REQUEST_BYTE_TRANSFER_EVENT:
                    this.bytesWritten += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int)(this.bytesWritten * 100.0 / this.totalBytes);
                        System.out.println(bytes + " bytes have been written at this time, upload progress: " +
                                percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" +
                                "(" + this.bytesWritten + "/...)");
                    }
                    break;

                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                    break;

                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                    break;

                default:
                    break;
            }
        }

        @SuppressWarnings("unused")
        public boolean isSucceed() {
            return succeed;
        }
    }

    /**
     * 获取下载进度回调
     *
     */
    private static class GetObjectProgressListener implements ProgressListener {

        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to download......");
                    break;

                case RESPONSE_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
                    break;

                case RESPONSE_BYTE_TRANSFER_EVENT:
                    this.bytesRead += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                        System.out.println(bytes + " bytes have been read at this time, download progress: " +
                                percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" +
                                "(" + this.bytesRead + "/...)");
                    }
                    break;

                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
                    break;

                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
                    break;

                default:
                    break;
            }
        }

        @SuppressWarnings("unused")
        public boolean isSucceed() {
            return succeed;
        }
    }
    /**
     * 表单上传
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return
     * @throws Exception
     */
    private static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap)
            throws Exception {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Entry<String, String>> iter = textMap.entrySet().iterator();
                int i = 0;

                while (iter.hasNext()) {
                    Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();

                    if (inputValue == null) {
                        continue;
                    }

                    if (i == 0) {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }

                    i++;
                }
                out.write(strBuf.toString().getBytes());
            }

            if (fileMap != null) {
                Iterator<Entry<String, String>> iter = fileMap.entrySet().iterator();

                while (iter.hasNext()) {
                    Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    if (inputValue == null) {
                        continue;
                    }

                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type: " + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }

                StringBuffer strBuf = new StringBuffer();
                out.write(strBuf.toString().getBytes());
            }

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.err.println("Send post request exception: " + e);
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }

        return res;
    }
    /**
     * 分片上传
     * @author 32974
     *
     */
    private static class PartUploader implements Runnable {

        private File localFile;
        private long startPos;
        private long partSize;
        private int partNumber;
        private String uploadId;
        private String bucketName;
        private String key;
        private OSSClient client;
        private List<PartETag> partETags;

        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId,
                            String bucketName, String key, OSSClient client, List<PartETag> partETags) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
            this.bucketName = bucketName;
            this.key = key;
            this.client = client;
            this.partETags = partETags;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);
                instream.skip(this.startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                System.out.println("Part#" + this.partNumber + " done\n");
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 追加上传文件
     * @param bucketName 需要追加对象的bucket名称
     * @param key 验证key
     * @param obj 需要追加的对象（inputStream和file任选一种）
     * @throws RrbSystemException
     * @throws IOException
     */
    public static void appendObject(AppendObjectRequest appendObjectRequest) throws IOException, RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("在" + appendObjectRequest.getBucketName() + "bucket中追加对象：" + appendObjectRequest.getKey());
            OSSObject object = client.getObject(appendObjectRequest.getBucketName(), appendObjectRequest.getKey());
            Long position = object.getObjectMetadata().getContentLength();
            client.appendObject(appendObjectRequest.withPosition(position));
            object.getObjectContent().close();
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 创建一个bucket
     * @param bucketName 要创建的bucket的名称（名称是唯一的 ，不能重复）
     * @param acl 创建的bucket的权限（默认是私有读写，不传的话则以默认权限创建bucket）通过CannedAccessControlList枚举选择
     * 1、默认：default，
     * 2、私有读写：private，
     * 3、公共读私有写：public-read，
     * 4、公共读写：public-read-write，
     * @throws RrbSystemException
     */
    public static void createBucket(String bucketName, CannedAccessControlList acl) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("开始创建名为" + bucketName + "的bucket");
            System.out.println("开始创建名为" + bucketName + "的bucket");
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            if (acl != null) {
                createBucketRequest.setCannedACL(acl);
            }
            client.createBucket(createBucketRequest);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 查看Bucket所属的数据中心位置信息。
     * @param bucketName
     * @return
     * @throws RrbSystemException
     */
    public static String getBucketLocationByName(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        System.out.println(client.getEndpoint().toString());
        try {
            String location = client.getBucketLocation(bucketName);
            return location;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 获取已有的bucket集合
     * @param listBucketsRequest 列举bucket请求对象
     * @return
     * @throws RrbSystemException
     */
    public static List<Bucket> listBucket(ListBucketsRequest listBucketsRequest) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("获取bucket列表\n");
            BucketList listBuckets = client.listBuckets(listBucketsRequest);
            return listBuckets.getBucketList();
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 根据bucket的名称获取bucket的权限
     * @param bucketName bucket名称
     * @return
     * @throws RrbSystemException
     */
    public static String getBucketACLByName(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            AccessControlList acl = client.getBucketAcl(bucketName);
            return acl.toString();
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }

    /**
     * 设置bucket的权限
     * @param bucketName 要设置权限的bucket名称
     * @param acl 需要设置的bucket的权限,通过CannedAccessControlList枚举选择
     * 1、默认：default，
     * 2、私有读写：private，
     * 3、公共读私有写：public-read，
     * 4、公共读写：public-read-write，
     * @throws RrbSystemException
     */
    public static void setBucketAcces(String bucketName, CannedAccessControlList acl) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            client.setBucketAcl(bucketName, acl);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 创建指定名称的bucket下的生命周期配置规则文件
     * 规则文件对象中：
     * 1、ruleid如果没有设置的话，会自动生成一个唯一的id
     * 2、如果希望配置规则以天为单位生效，则只设置expirationDays属性，
     * 如果希望在具体哪个日期前生效，则设置createdBeforeDate属性，
     * 如果两个都设置了的话，默认以createdBeforeDate配置。
     * @param bucketName bucket名称
     * @param rules 规则文件对象
     * @throws RrbSystemException
     */
    public static void setBucketLifecycle(String bucketName, List<LifecycleRule> rules) throws RrbSystemException {
        OSSClient client = getOSSClient();
        if (rules == null || rules.size() == 0) {
            logger.error("参数错误");
            throw new BusinessException(FunctionExceptions.System.PARAMETER_ERROR);
        }
        try {
            SetBucketLifecycleRequest request = new SetBucketLifecycleRequest(bucketName);
            for (LifecycleRule rule : rules) {
                String ruleId = rule.getId();
                Date createdBeforeDate = rule.getCreatedBeforeDate();
                Integer expirationDays = rule.getExpirationDays();
                String matchPrefix = rule.getPrefix();
                RuleStatus status = rule.getStatus();
                if (createdBeforeDate != null && expirationDays != null) {
                    request.AddLifecycleRule(new LifecycleRule(ruleId, matchPrefix, status, parseISO8601Date(formatISO8601Date(createdBeforeDate))));
                } else if (createdBeforeDate != null) {
                    request.AddLifecycleRule(
                            new LifecycleRule(ruleId, matchPrefix, status, parseISO8601Date(formatISO8601Date(createdBeforeDate))));
                } else if (expirationDays != null) {
                    request.AddLifecycleRule(new LifecycleRule(ruleId, matchPrefix, status, expirationDays));
                } else {
                    logger.error("参数错误,规则生效时间不能为空");
                    throw new BusinessException(FunctionExceptions.System.PARAMETER_ERROR);
                }
                logger.debug("开始创建名为：" + bucketName + "的bucket下的生命周期配置规则文件");
                client.setBucketLifecycle(request);
            }
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 获取指定名称的bucket的生命周期配置规则
     * @param bucketName 要获取配置规则的bucketName
     * @return
     * @throws RrbSystemException
     */
    public static List<LifecycleRule> getBucketLifecycle(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            List<LifecycleRule> rules = client.getBucketLifecycle(bucketName);
            return rules;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 1、本操作会删除指定Bucket的所有的生命周期规则。此后，该Bucket中不会有Object被自动删除。
     * 2、如果Bucket或Lifecycle不存在，返回404 Not Found错误，错误码：NoSuchBucket或NoSuchLifecycle。
     * 3、只有Bucket的拥有者才能删除Bucket的Lifecycle配置。如果试图操作一个不属于你的Bucket，OSS返回403 Forbidden错误，错误码：AccessDenied。
     * @param bucketName 要删除配置文件的bucketName
     * @throws RrbSystemException
     */
    public static void deleteBucketLifecycle(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("删除名为：" + bucketName + "下的所有配置文件");
            client.deleteBucketLifecycle(bucketName);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 开启指定名称的bucket的日志记录功能
     * @param bucketName 需要开启日志记录的bucket名称
     * @param targetBucket 日志保存的bucket名称（日志也可以保存在开启日志的哪个bucket里面）
     * @param logPrefix 日志文件的前缀（日志生成的格式为：<SourceBucket>-YYYY-mm-DD-HH-MM-SS-UniqueString）
     * 命名规则中，TargetPrefix由用户指定；
     * YYYY, mm, DD, HH, MM和SS分别是该Object被创建时的阿拉伯数字的年，月，日，小时，分钟和秒（注意位数）；
     * UniqueString为OSS系统生成的字符串。
     * @throws RrbSystemException
     */
    public static void setBucketLogging(String bucketName, String targetBucket, String logPrefix) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("开启bucket日志记录功能，bucket名称：" + bucketName + "，日志保存bucket名称：" + targetBucket + "，日志文件前缀：" + logPrefix);
            SetBucketLoggingRequest request = new SetBucketLoggingRequest(bucketName);
            request.setTargetBucket(targetBucket);
            request.setTargetPrefix(logPrefix);
            client.setBucketLogging(request);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 关闭指定bucket的日志功能
     * 1、如果Bucket不存在，返回404 no content错误，错误码：NoSuchBucket。
     * 2、只有Bucket的拥有者才能关闭Bucket访问日志记录功能。
     * 如果试图操作一个不属于你的Bucket，OSS返回403 Forbidden错误，错误码：AccessDenied。
     * 3、如果目标Bucket并没有开启Logging功能，仍然返回HTTP状态码 204。
     * @param bucketName 要关闭的bucket名称
     * @throws RrbSystemException
     */
    public static void deleteBucketLogging(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("关闭bucket日志记录功能，bucket名称：" + bucketName);
            client.deleteBucketLogging(bucketName);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 为指定的bucket设置访问白名单
     * @param bucketName 要设置白名单的bucket名称
     * @param referers 白名单集合，
     * 可使用通配符‘*’|‘?’
     * 1、星号“*”：可以使用星号代替0个或多个字符。
     * 如果正在查找以AEW开头的一个文件，但不记得文件名其余部分，
     * 如：可以输入AEW*，查找以AEW开头的所有文件类型的文件。
     * 2、问号“?”：可以使用问号代替一个字符。
     * 如果输入m?,查找以m开头的一个字符结尾文件类型的文件。
     * @param allowEmptyReferer
     * @throws RrbSystemException
     */
    public static void setBucketReferer(String bucketName, List<String> referers, Boolean allowEmptyReferer) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("设置名为：" + bucketName + "的访问白名单");
            BucketReferer r = new BucketReferer();
            r.setRefererList(referers);
            r.setAllowEmptyReferer(allowEmptyReferer);
            client.setBucketReferer(bucketName, r);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 获取指定名称的bucket的referer配置信息
     * @param bucketName 要获取白名单的bucket名称
     * @return
     * @throws RrbSystemException
     */
    public static BucketReferer getBucketRefererCfg(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("获取名为：" + bucketName + "的referer配置信息");
            BucketReferer r = new BucketReferer();
            r = client.getBucketReferer(bucketName);
            return r;

        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 清空指定名称的bucket的referer配置信息
     * @param bucketName 需要清除的bucket名称
     * @throws RrbSystemException
     */
    public static void clearBucketRefererList(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("清空名为：" + bucketName + "的referer配置list");
            BucketReferer r = new BucketReferer();
            r = client.getBucketReferer(bucketName);
            r.clearRefererList();
            client.setBucketReferer(bucketName, r);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 删除指定名称的bucket
     * @param bucketName
     * @throws RrbSystemException
     */
    public static void deleteBucket(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("删除名为：" + bucketName + "的bucket");
            client.deleteBucket(bucketName);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 设置指定名称的bucket为静态网页托管模式
     * @param bucketName bucket名称
     * @param indexDocument 索引页面
     * @param errorDocument 错误页面（可选）
     * @param notfoundDoc 返回404时使用的文件（指定了错误页面是，返回404错误时使用的文件名 不能为空）
     * @throws RrbSystemException
     */
    public static void setBucketWebsite(String bucketName, String indexDocument, String errorDocument, String notfoundDoc)
            throws RrbSystemException {
        OSSClient client = getOSSClient();
        if(StringUtils.isBlank(indexDocument)){
            logger.error("索引页面不能为空");
            throw new BusinessException(FunctionExceptions.System.PARAMETER_ERROR);
        }else if(StringUtils.isNotBlank(errorDocument) && StringUtils.isBlank(notfoundDoc)){
            logger.error("指定了错误页面是，返回404错误时使用的文件名 不能为空");
            throw new BusinessException(FunctionExceptions.System.PARAMETER_ERROR);
        }
        try {
            logger.debug("将名为：" + bucketName + "的bucket，设置成静态网站托管模式。");
            SetBucketWebsiteRequest request = new SetBucketWebsiteRequest(bucketName);
            request.setIndexDocument(indexDocument);
            request.setErrorDocument(errorDocument);
            request.setKey(notfoundDoc);
            client.setBucketWebsite(request);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 关闭bucket的静态网站托管模式。
     * @param bucketName
     * @throws RrbSystemException
     */
    public static void setBucketWebsite(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("关闭名为：" + bucketName + "的bucket的静态网站托管模式。");
            client.deleteBucketWebsite(bucketName);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 设置bucket的跨域共享规则
     * 1、每个存储空间最多只能使用10条规则。
     * 2、AllowedOrigins和AllowedMethods都能够最多支持一个”*”通配符。”*”表示对于所有的域来源或者操作都满足。
     * 3、而AllowedHeaders和ExposeHeaders不支持通配符。
     * @param bucketName
     * @param corsRules 需要设置的跨域共享规则集合
     * 其中有：
     * 1、ArrayList<String> allowedOrigin 指定允许跨域请求的来源
     * 2、ArrayList<String> allowedMethod 指定允许的跨域请求方法(GET/PUT/DELETE/POST/HEAD)
     * 3、ArrayList<String> allowedHeader 控制在OPTIONS预取指令中Access-Control-Request-Headers头中指定的header是否允许。
     * 4、ArrayList<String> exposedHeader 指定允许用户从应用程序中访问的响应头
     * 5、Integer maxAgeSeconds 指定浏览器对特定资源的预取(OPTIONS)请求返回结果的缓存时间,单位为秒。
     * @throws RrbSystemException
     */
    public static void setBucketCORSO(String bucketName, List<CORSRule> corsRules) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("设置名为：" + bucketName + "的跨域共享规则");
            SetBucketCORSRequest request = new SetBucketCORSRequest(bucketName);
            request.setCorsRules(corsRules);
            client.setBucketCORS(request);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 获取指定名称的bucket的跨域访问规则
     * @param bucketName
     * @return 跨域访问规则的集合
     * @throws RrbSystemException
     */
    public static List<CORSRule> getBucketCORS(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("获取名为：" + bucketName + "的跨域共享规则");
            List<CORSRule> rules = client.getBucketCORSRules(bucketName);
            return rules;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 删除指定的bucket的跨域访问规则（清空所有规则）
     * @param bucketName
     * @throws RrbSystemException
     */
    public static void deleteBucketCORS(String bucketName) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("删除名为：" + bucketName + "的跨域共享规则");
            client.deleteBucketCORSRules(bucketName);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 在指定的bucket中创建一个文件夹
     * @param bucketName
     * @param keySuffixWithSlash
     * @throws RrbSystemException
     */
    public static void createFolder(String bucketName, String keySuffixWithSlash) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("在名为：" + bucketName + "的bucket中创建一个文件夹" + keySuffixWithSlash);
            client.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * put上传object
     * @param putObjectRequest put上传请求对象
     * @param withProgress 是否需要显示进度条
     * putObject/getObject/uploadPart都支持进度条功能；
     * uploadFile/downloadFile不支持进度条功能。
     * @throws RrbSystemException
     * @throws IOException
     */
    public static void putObject(PutObjectRequest putObjectRequest, boolean withProgress) throws RrbSystemException, IOException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("上传object到名为：" + putObjectRequest.getBucketName() + "的bucket");
            PutObjectResult putObjectResult;
            if(withProgress){
                putObjectResult = client.putObject((PutObjectRequest) putObjectRequest.<PutObjectRequest>withProgressListener(new PutObjectProgressListener()));
            }else{
                putObjectResult = client.putObject(putObjectRequest);
            }
            if(putObjectRequest.getCallback() != null){//如果设置了回调函数
                // 读取上传回调返回的消息内容
                byte[] buffer = new byte[1024];
                putObjectResult.getCallbackResponseBody().read(buffer);
                // 一定要close，否则会造成连接资源泄漏
                putObjectResult.getCallbackResponseBody().close();
            }
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * post提交表单方式上传文件
     * @param textMap 表单域
     * 其中包含了：
     * 1、key上传对象的key名称 : key为key
     * 2、bucketName bucket名称 ：key为bucketName
     * 3、localFilePath本地文件路径： key为localFilePath;
     * 4、policy 规定了请求的表单域的合法性。不包含policy表单域的请求被认为是匿名请求，并且只能访问public-read-write的bucket。
     * key为policy
     * @throws Exception
     */
    public static void postObject(Map<String, String> textMap) throws Exception {
        OSSClient client = getOSSClient();
        String endpoint = "";
        URI endPointUri = client.getEndpoint();
        if (endPointUri != null) {
            endpoint = endPointUri.toString();
        } else {
            logger.equals("endpotin没有获取到");
            throw new RrbSystemException("endptoin没有获取到");
        }
        try {
            // 提交表单的URL为bucket域名
            String urlStr = endpoint.replace("http://", "http://" + textMap.get("bucketName") + ".");
            textMap.put("Content-Disposition", "attachment;filename=" + textMap.get("localFilePath"));
            // OSSAccessKeyId
            textMap.put("OSSAccessKeyId", OssConfig.accessKeyId);
            String encodePolicy = new String(Base64.encodeBase64(textMap.get("policy").getBytes()));
            textMap.put("policy", encodePolicy);
            // Signature
            String signaturecom = com.aliyun.oss.common.auth.ServiceSignature.create()
                    .computeSignature(OssConfig.accessKeySecret, encodePolicy);
            textMap.put("Signature", signaturecom);

            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("file", textMap.get("localFilePath"));

            String ret = formUpload(urlStr, textMap, fileMap);
            logger.debug("Post Object [" + textMap.get("key") + "] to bucket [" + textMap.get("bucketName") + "]");
            logger.debug("post reponse:" + ret);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 分片上传文件
     * @param bucketName 要上传文件的bucket空间名
     * @param key 要上传的object名称
     * @param uploadFile 需要上传的本地文件
     * @param partSize 每个分片的大小
     * @throws RrbSystemException
     */
    public static void multipartUpload(String bucketName, String key, File uploadFile, final long partSize) throws RrbSystemException {
        OSSClient client = getOSSClient();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
        try {
            logger.debug("分片上传文件到：" + bucketName + "bucket空间\n");
            logger.debug("初始化分片上传请求实例\n");
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
            InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
            String uploadId = result.getUploadId();
            long fileLength = uploadFile.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RrbSystemException("分片总数不能超过10000\n");
            } else {
                logger.debug("分片总数为" + partCount + "个\n");
            }
            logger.debug("开始上传分片...\n");
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUploader(uploadFile, startPos, curPartSize, i + 1, uploadId, bucketName, key, client, partETags));
            }
            logger.debug("等待完成分片上传\n");
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.debug("验证分片是否全部上传成功\n");
            if (partETags.size() != partCount) {
                throw new IllegalStateException("有部分分片上传失败\n");
            } else {
                logger.debug(key + "object的所有分片上传完成\n");
            }

            Collections.sort(partETags, new Comparator<PartETag>() {
                @Override
                public int compare(PartETag p1, PartETag p2) {
                    return p1.getPartNumber() - p2.getPartNumber();
                }
            });
            logger.debug("完成分片上传\n");
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
            client.completeMultipartUpload(completeMultipartUploadRequest);
            executorService.shutdown();
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }
    /**
     * 断点续传文件
     * @param uploadFileRequest 断点续传请求对象
     * @throws Throwable
     */
    public static void uploadEnableCheckpoint(UploadFileRequest uploadFileRequest) throws Throwable{
        OSSClient client = getOSSClient();
        try {
            client.uploadFile(uploadFileRequest);
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
    }

    /**
     * 下载指定的一个bucket中的指定key的一个对象
     * @param getObjectRequest 获取object请求对象
     * @param withProgress 是否显示进度条
     * @return 返回下载到的object对象
     * @throws RrbSystemException
     */
    public static OSSObject getObject(GetObjectRequest getObjectRequest, boolean withProgress) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("下载：" + getObjectRequest.getBucketName() + "的bucket中的" + getObjectRequest.getKey() + "对象");
            OSSObject object;
            if(withProgress){
                object = client.getObject((GetObjectRequest) getObjectRequest.withProgressListener(new GetObjectProgressListener()));
            }else{
                object = client.getObject(getObjectRequest);
            }
            return object;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 断点续传下载文件
     * @param downloadFileRequest 断点续传下载文件请求对象
     * @return
     * @throws Throwable
     */
    public static DownloadFileResult getObject(DownloadFileRequest downloadFileRequest) throws Throwable {
        OSSClient client = getOSSClient();
        try {
            logger.debug("断点续传下载：" + downloadFileRequest.getBucketName() + "的bucket中的" + downloadFileRequest.getKey() + "对象");
            DownloadFileResult downloadRes = client.downloadFile(downloadFileRequest);
            return downloadRes;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 列出所指定bucket名称的所有的object
     * @param listObjectsRequest
     * @return
     * @throws Throwable
     */
    public static List<OSSObjectSummary> listObjects(ListObjectsRequest listObjectsRequest) throws Throwable {
        OSSClient client = getOSSClient();
        try {
            logger.debug("列出：" + listObjectsRequest.getBucketName() + "的bucket中的对象");
            ObjectListing objectListing = client.listObjects(listObjectsRequest);
            List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            return objectSummaries;
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return null;
    }
    /**
     * 删除指定bucket中的一个或多个对象
     * @param bucketName
     * @param keys object的key的集合
     * @return 返回删除了的object的个数
     * @throws RrbSystemException
     */
    public static int deleteObjects(String bucketName,  List<String> keys) throws RrbSystemException {
        OSSClient client = getOSSClient();
        try {
            logger.debug("删除名为：" + bucketName + "的bucket中的对象");
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            return deletedObjects.size();
        } catch (OSSException oe) {
            OSSExceptionLoggerPrint(oe);
        } catch (ClientException ce) {
            clientExceptionLoggerPrint(ce);
        } finally {
            client.shutdown();
        }
        return 0;
    }

    public static String getOssResourceUri(String bucketName, String key) {
        return StringUtils.isNotEmpty(key)&&key.startsWith("http")?key:String.format("https://%s.%s/%s", bucketName, OssConfig.endpoint, key);
    }

    public static void main(String[] args) throws RrbSystemException, IOException {
        //    	File file = new File("E://DevelopData/settings.xml");
        //		PutObjectRequest putObjectRequest = new PutObjectRequest("kuaihedev", "test_object", file);
        //		//createBucket("mybucket-test", CannedAccessControlList.PublicReadWrite);
        //    	//bucket list  kuaihedev testwangwei
        //    	//kuaihedev object name 500_0e61_2e4d40ea_2e4d40ea.jpg
        //    	try {
        //			ListObjectsRequest listObjectsRequest = new ListObjectsRequest("kuaihedev");
        //			List<OSSObjectSummary> listObjects = listObjects(listObjectsRequest);
        //			for (OSSObjectSummary ossObjectSummary : listObjects) {
        //				System.out.println(ossObjectSummary.getKey());
        //			}
        //    		/*List<String> l = new ArrayList<>();
        //    		l.add("test_object");
        //    		deleteObjects("kuaihedev", l);*/
        //		} catch (Throwable e) {
        //			e.printStackTrace();
        //		}
        File file = new File("/Users/stone/Downloads/images/dota/ta.jpeg");
        String key = UUID.randomUUID().toString();
        PutObjectRequest req = new PutObjectRequest("kuaihedev", key, file);
        OssUtil.putObject(req, false);
        System.out.println(OssUtil.getOssResourceUri(req.getBucketName(), req.getKey()));
    }
}
