package com.example.hci.common;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity  implements Serializable {

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Entity other = (Entity) obj;
        if(id == null || other.id == null){
            return false;
        }else if(! id.equals(other.id)){
            return false;
        }
        return true;
    }

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
}
