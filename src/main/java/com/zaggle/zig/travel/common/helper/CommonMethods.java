package com.zaggle.zig.travel.common.helper;



import com.zaggle.zig.travel.common.dto.ErrorResponse;
import com.zaggle.zig.travel.common.dto.SuccessfulResponse;
import com.zaggle.zig.travel.common.dto.error.ValidationError;
import com.zaggle.zig.travel.common.dto.meta.ErrorMeta;
import com.zaggle.zig.travel.common.dto.meta.Header;
import com.zaggle.zig.travel.common.dto.meta.SuccessMeta;
import com.zaggle.zig.travel.utils.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class CommonMethods {

    public ErrorResponse buildErrorResponse(List<ValidationError> errors, String errorMessage) {

        ErrorResponse errorResponse = new ErrorResponse();
//        Error error = new Error();
//        error.setCode("PW001");
//        error.setMessage("Exception Occurred");

        errorResponse.setMeta(buildErrorMeta());
        if(errors == null) {
            errorResponse.setMessage(errorMessage);
        }

        else {
            errorResponse.setData(errors);
            errorResponse.setMessage(Constant.BAD_REQUEST);
        }
        return errorResponse;

    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public SuccessMeta buildSuccessMeta(){
        SuccessMeta successMeta = new SuccessMeta();
        successMeta.setRequestId(UUID.randomUUID().toString());
        successMeta.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());

        return successMeta;
    }

    public <T> SuccessfulResponse<T> createSuccessResponse(List<T> data) {
        return SuccessfulResponse.<T>builder()
                .data(data)
                .meta(buildSuccessMeta())
                .message(Constant.SUCCESS)
                .build();
    }

    public ErrorMeta buildErrorMeta(){
        HttpServletRequest request = getRequest();
        Header header = new Header();
        header.setPath(request.getRequestURI());
        header.setMethod(request.getMethod());

        ErrorMeta errorMeta = new ErrorMeta();
        errorMeta.setRequestId(UUID.randomUUID().toString());
        errorMeta.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        errorMeta.setHeaders(header);

        return errorMeta;
    }







}
