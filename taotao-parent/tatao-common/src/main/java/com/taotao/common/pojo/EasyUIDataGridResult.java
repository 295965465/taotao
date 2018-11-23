package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * datagrid展示数据,因为是maven聚合项目所以要实现序列化
 */
public class EasyUIDataGridResult implements Serializable {
          private Integer total;
          private List rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
