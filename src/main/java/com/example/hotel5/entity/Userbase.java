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
public class Userbase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表
     */
    private Integer id;

    private String phone;

    private String password;

    private Integer isAuthe;

    private Integer isBlack;


}
