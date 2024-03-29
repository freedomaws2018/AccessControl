package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.IdClass.Wf8266DetailId;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_wf8266_detail")
@IdClass(Wf8266DetailId.class)
public class Wf8266Detail {

  /** 執行動作名稱 **/
  @Id
  @Column(name = "name")
  private String name;

  @Id
  @Column(name = "wf8266_sn")
  private String wf8266Sn;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "wf8266_sn", referencedColumnName = "sn", insertable = false, updatable = false, nullable = true)
  private Wf8266 wf8266;

  /** 是否啟用 **/
  @Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean isUse = new Boolean(false);

  /** 執行指令模式 **/
  @Column(name = "cmd")
  private String cmd;

  /**
   * 繼電器<BR>
   * Relay : 全部, 表示 GPIO 5,4,12,13 Relay1 : 表示 GPIO 5 Relay2 : 表示 GPIO 4 Relay3 :
   * 表示 GPIO 12 Relay4 : 表示 GPIO 13
   **/
  @Column(name = "relay")
  private Integer relay;

  /**
   * 觸發行為 <BR>
   * [0:開啟] [1:關閉] [2:反向 Toggle] [3:按鍵 0->1->0] [4:按鍵 1->0->1]
   **/
  @Column(name = "value")
  private Integer value;

  /** 出發前 回應 **/
  @Column(name = "reply1")
  private String reply1;

  /** 出發後 回應 **/
  @Column(name = "reply2")
  private String reply2;

  public String getTriggerUrl() {
//		 https://service.wf8266.com/api/mqtt/SN/CMD/KEY/Value
    if (wf8266 != null) {
      return String.format("https://service.wf8266.com/api/mqtt/%s/%s/%s/%s", wf8266Sn, cmd + relay, wf8266.getKey(),
          value);
    } else {
      return null;
    }
  }

}
