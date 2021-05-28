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
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Roomsocket implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String roomNum;

    private String roomTemperature;

    private String roomWet;

    private String roomPM;

    private String roomNoise;


}
