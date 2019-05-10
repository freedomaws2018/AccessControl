package com.example.demo.DataBase.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;

import com.example.demo.DataBase.Entity.Base.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_location")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Location extends BaseEntity {

	/** 地點對應ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 地點名稱 **/
	@Column(name = "name")
	private String name;

	/** 地點地址 **/
	@Column(name = "address")
	private String address;

	/** 地點電話 **/
	@Column(name = "phone")
	private String phone;

//	/** 負責人 - 對應 Employee **/
	@Column(name = "keepers", columnDefinition = "jsonb")
	private String keepers;

	public void setKeepers(List<Long> keepers) {
		this.keepers = new Gson().toJson(keepers);
	}

	public List<Long> getKeepers() {
		return new Gson().fromJson(this.keepers, new TypeToken<List<Long>>() {
		}.getType());
	}

//	/** 對應的設備 **/
//	@OneToMany(mappedBy = "locationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Wf8266> wf8266s;

//	@OneToMany(mappedBy = "lineuser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<MappingLineUserAndLocation> lineusers;
}
