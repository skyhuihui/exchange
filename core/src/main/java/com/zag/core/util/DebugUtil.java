package com.zag.core.util;

import com.zag.core.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据截至日期来判断是否开启debug模式
 * 
 * @author stone
 * @since 2017年8月6日
 * @usage
 * @reviewer
 */
public class DebugUtil {

	private static final String deadlineString = "2016-11-11";
	private static final Date deadline;
	static {
		try {
			deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineString);
		} catch (ParseException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 超过预定日期后,以抛出异常的方式阻止程序运行,用于标记在正式环境中需要清理的debug代码
	 */
	public static void debugMark() {
		debugMark(null);
	}

	public static boolean isDebugEnabled() {
		return new Date().compareTo(deadline) < 0;
	}

	public static void debugMark(String msg) {
		if (!isDebugEnabled()) {
			if (StringUtils.isNotBlank(msg)) {
				throw new SystemException("deadline:" + deadlineString + "," + msg);
			}
			throw new SystemException(deadlineString + "之后所有标记点需要清除才能运行");
		}
	}

	public static String toString(Object obj) {
		return toString(obj, "<null>");
	}

	public static String toString(Object obj, String ifNull) {
		if (obj == null)
			return ifNull;
		if (isDebugEnabled())
			return ToStringBuilder.reflectionToString(obj, JSON_STYLE_FIXED);
		return ToStringBuilder.reflectionToString(obj);
	}

	public static final JsonToStringStyle JSON_STYLE_FIXED = new JsonToStringStyle();

	
	
	
	//******************************************************************************TODO inner class
	
	/**
	 * copy from {@link #org
	 * .apache.commons.lang3.builder.ToStringStyle.JsonToStringStyle}
	 * <p>
	 * 修复了枚举类型字段没有引号的问题
	 * 
	 * @author stone
	 * @since 2017年8月15日
	 * @usage
	 * @reviewer
	 */
	private static final class JsonToStringStyle extends ToStringStyle {

		private static final long serialVersionUID = 1L;

		/**
		 * The summary size text start <code>'&gt;'</code>.
		 */
		private String FIELD_NAME_PREFIX = "\"";

		/**
		 * <p>
		 * Constructor.
		 * </p>
		 *
		 * <p>
		 * Use the static constant rather than instantiating.
		 * </p>
		 */
		JsonToStringStyle() {
			super();

			this.setUseClassName(false);
			this.setUseIdentityHashCode(false);

			this.setContentStart("{");
			this.setContentEnd("}");

			this.setArrayStart("[");
			this.setArrayEnd("]");

			this.setFieldSeparator(",");
			this.setFieldNameValueSeparator(":");

			this.setNullText("null");

			this.setSummaryObjectStartText("\"<");
			this.setSummaryObjectEndText(">\"");

			this.setSizeStartText("\"<size=");
			this.setSizeEndText(">\"");
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, array, fullDetail);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {

			checkArgs(fieldName, fullDetail);

			super.append(buffer, fieldName, value, fullDetail);
		}

		private void checkArgs(String fieldName, Boolean fullDetail) {
			if (fieldName == null) {
				throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
			}
			if (!isFullDetail(fullDetail)) {
				throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {

			if (value == null) {

				appendNullText(buffer, fieldName);
				return;
			}

			if (value.getClass() == String.class) {
				//超长省略
				boolean toolong = value.toString().length() > 60;
				appendValueAsString(buffer, toolong ? value.toString().substring(0,60)+"......" : value.toString());
				return;
			} else if (value instanceof Enum && value != null) {
				appendValueAsString(buffer, ((Enum) value).name());
			}

			buffer.append(value);
		}

		/**
		 * Appends the given String in parenthesis to the given StringBuffer.
		 * 
		 * @param buffer
		 *            the StringBuffer to append the value to.
		 * @param value
		 *            the value to append.
		 */
		private void appendValueAsString(StringBuffer buffer, String value) {
			buffer.append("\"" + value + "\"");
		}

		@Override
		protected void appendFieldStart(StringBuffer buffer, String fieldName) {

			if (fieldName == null) {
				throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
			}

			super.appendFieldStart(buffer, FIELD_NAME_PREFIX + fieldName + FIELD_NAME_PREFIX);
		}

		/**
		 * <p>
		 * Ensure <code>Singleton</code> after serialization.
		 * </p>
		 *
		 * @return the singleton
		 */
		private Object readResolve() {
			return JSON_STYLE_FIXED;
		}

	}
}
