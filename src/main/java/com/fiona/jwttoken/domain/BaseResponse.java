package com.fiona.jwttoken.domain;


import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseResponse<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 5216759467342424430L;
    private T content;
    private Boolean status;
    private List<Message> messages;
    private String errorCode;

    public BaseResponse() {
        super();
        this.status = true;
    }

    public BaseResponse(T content, Boolean status, List<String> messages, String errorCode) {
        super();
        this.content = content;
        this.status = status;
        if (!CollectionUtils.isEmpty(messages)) {
            this.messages = messages.stream().map(o -> new Message(o)).collect(Collectors.toList());
        }
        this.errorCode = errorCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<Message> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public BaseResponse<T> withContent(T content) {
        this.setContent(content);
        return this;
    }

    public BaseResponse<T> withStatus(Boolean status) {
        this.setStatus(status);
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BaseResponse withErrorMessage(String errMsg) {
        this.getMessages().add(new Message(errMsg));
        return this.withStatus(false);
    }

    @SuppressWarnings("rawtypes")
    public BaseResponse withMessage(String msg) {
        this.getMessages().add(new Message(Types.MessageSeverity.INFO, msg));
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BaseResponse withMessage(Types.MessageSeverity messageSeverity, String msg) {
        this.getMessages().add(new Message(messageSeverity, msg));
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BaseResponse withMessage(int code, Types.MessageSeverity messageSeverity, String msg) {
        this.getMessages().add(new Message(code, messageSeverity, Types.MessageSource.UNKNOWN, msg));
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BaseResponse withMessage(int code, Types.MessageSeverity messageSeverity, Types.MessageSource source, String msg) {
        this.getMessages().add(new Message(code, messageSeverity, source, msg));
        return this;
    }

    public BaseResponse<T> withErrorCode(String errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }

    @SuppressWarnings("rawtypes")
    public static BaseResponse fromErrorMessage(String errMsg) {
        return new BaseResponse<>().withErrorMessage(errMsg).withStatus(false);
    }


    /**
     * <p>
     * BaseResponse.Message
     * </p>
     *
     * @author lina.feng
     * @history Mender:lina.feng；Date:2019年1月30日；
     */
    public static class Message implements Serializable {

        private static final long serialVersionUID = -5635676812152119256L;
        private String msg;
        private int code;
        private Types.MessageSeverity messageSeverity;
        private Types.MessageSource source;

        public Message() {
        }

        public Message(int code, Types.MessageSeverity messageSeverity, Types.MessageSource source, String message) {
            this.code = code;
            this.msg = message;
            this.messageSeverity = messageSeverity;
            this.source = source;
        }

        public Message(Types.MessageSeverity messageSeverity, String message) {
            this.msg = message;
            this.messageSeverity = messageSeverity;
        }

        public Message(String message) {
            this(9999, Types.MessageSeverity.ERROR, Types.MessageSource.UNKNOWN, message);
        }

        public Message(Throwable e) {
            this.code = 9999;
            this.source = Types.MessageSource.EXCEPTION;
            this.messageSeverity = Types.MessageSeverity.FATAL;
            this.msg = e.toString();
        }

        public Message(String message, Throwable e) {
            this.code = 9999;
            this.source = Types.MessageSource.EXCEPTION;
            this.messageSeverity = Types.MessageSeverity.FATAL;
            this.msg = message != null ? message : e.toString();
        }

        public static Message error(String message) {
            return new Message(9999, Types.MessageSeverity.ERROR, Types.MessageSource.UNKNOWN, message);
        }

        public static Message warning(String message) {
            return new Message(9999, Types.MessageSeverity.WARN, Types.MessageSource.UNKNOWN, message);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Types.MessageSeverity getMessageSeverity() {
            return messageSeverity;
        }

        public void setMessageSeverity(Types.MessageSeverity messageSeverity) {
            this.messageSeverity = messageSeverity;
        }

        public Types.MessageSource getSource() {
            return source;
        }

        public void setSource(Types.MessageSource source) {
            this.source = source;
        }
    }
}
