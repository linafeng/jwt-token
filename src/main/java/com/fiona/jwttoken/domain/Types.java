package com.fiona.jwttoken.domain;
/**
 * 集合简单枚举类
 * <p>
 *  module
 * </p>
 * @author lina.feng
 * @history Mender:lina.feng；Date:2019年2月20日；
 */
public class Types {
	private Types() {}
	public  enum MessageSeverity {
		INFO(1), WARN(2), ERROR(3), FATAL(4);

		private final int value;

		private MessageSeverity(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		public static Types.MessageSeverity fromString(String severity) {
			return valueOf(severity);
		}
	}

	public enum MessageSource {
		UNKNOWN, VALIDATION, EXCEPTION;
		public static Types.MessageSource fromString(String source) {
			try {
				return valueOf(source.replace(' ', '_'));
			} catch (Exception arg1) {
				return UNKNOWN;
			}
		}
		@Override
		public String toString() {
			return this.name().replace('_', ' ');
		}
	}
	

}
