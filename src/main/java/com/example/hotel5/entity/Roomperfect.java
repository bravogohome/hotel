package com.example.hotel5.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gohome
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Roomperfect implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roomNum;

    private Integer orderNum;


}
