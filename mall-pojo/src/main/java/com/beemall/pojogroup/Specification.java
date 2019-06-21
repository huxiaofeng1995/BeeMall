package com.beemall.pojogroup;

import com.beemall.pojo.TbSpecification;
import com.beemall.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 9:17
 * @description：
 * @modified By：
 */
public class Specification implements Serializable {
    private TbSpecification specification;
    private List<TbSpecificationOption> specificationOptionList;

    public TbSpecification getSpecification() {
        return specification;
    }
    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }
    public List<TbSpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }
    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

}
