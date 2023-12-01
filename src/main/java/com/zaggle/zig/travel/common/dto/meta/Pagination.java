package com.zaggle.zig.travel.common.dto.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination implements Serializable {
    private Integer total;
    private Integer pages;
    private Integer page;
    private Integer limit;
    private Link links;
}
