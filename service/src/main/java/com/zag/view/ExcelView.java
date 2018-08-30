package com.zag.view;


import com.zag.core.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author zhangcheng
 * @date 2016/10/6
 * @see
 * @reviewer
 */
public class ExcelView extends AbstractExcelView {
    @Override
    public void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                   HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String filename = ExcelUtil.encodeFilename((map.get("filename").toString() + System.currentTimeMillis()).concat(".xls"), httpServletRequest);
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
