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
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roomNum;

    private String roomType;

    private Double roomMoney;

    private Integer orderNum;

    private String roomTemperature;

    private String roomWet;

    private String roomPM;

    private String roomNoise;

    private Float roomScore;

    private Integer peopleNum;


}
