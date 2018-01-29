/**
  * Copyright 2017 bejson.com 
  */
package com.boliangshenghe.jsontransform.vo.internaltw;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2017-11-24 15:42:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Name {

	public String countyName;
    @JsonProperty
    public String ProCapName;
    public void setCountyName(String countyName) {
         this.countyName = countyName;
     }
     public String getCountyName() {
         return countyName;
     }

    public void setProCapName(String ProCapName) {
         this.ProCapName = ProCapName;
     }
     public String getProCapName() {
         return ProCapName;
     }

}