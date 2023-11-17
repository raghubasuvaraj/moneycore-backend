package com.moneycore.entity.log;

import com.moneycore.entity.Menu;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_menu")
public class MenuLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;                 // primary key auto increment
    @Column(name = "log_timestamp")
    private Date logTimestamp;
    @Column(name = "log_operation")
    private String logOperation;        // [ INSERT, UPDATE, DELETE]
    @Column(name = "log_user")
    private String logUser;             // operation user

    // other log attributes
    @Column(name = "menu_code")
    private String menuCode;
    @Column(name = "menu_id",unique = true)
    private String menuId;
    @Column(name = "name")
    private String name;
    @Column(name = "workspace")
    private String workSpace;
    @Column(name = "module")
    private String module;
    @Column(name = "wording")
    private String wording;
    @Column(name = "type")
    private String type;
    @Column(name = "menu_level")
    private int menuLevel;
    @Column(name = "menu_order")
    private int menuOrder;
    @Column(name = "url")
    private String url;
    @Column(name = "key_words")
    private String keyWords;
    @Column(name = "params")
    private String params;
    @Column(name = "user_create")
    private String userCreate;
    @Column(name = "date_create")
    @Temporal(TemporalType.DATE)
    private Date dateCreate;
    @Column(name = "user_modif")
    private String userModif;
    @Column(name = "date_modif")
    @Temporal(TemporalType.DATE)
    private Date dateModif;

    public MenuLog(){
    }

    public MenuLog(Menu entityInst, String action, String loginUser) {
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = loginUser;
        // others
        this.menuCode = entityInst.getMenuCode();
        this.menuId = entityInst.getMenuId();
        this.name = entityInst.getName();
        this.workSpace = entityInst.getWorkspace();
        this.module = entityInst.getModule();
        this.wording = entityInst.getWording();
        this.type = entityInst.getType();
        this.menuLevel = entityInst.getMenuLevel();
        this.menuOrder = entityInst.getMenuOrder();
        this.url = entityInst.getUrl();
        this.keyWords = entityInst.getKeyWords();
        this.params = entityInst.getParams();
        this.userCreate = entityInst.getUserCreate();
        this.dateCreate = entityInst.getDateCreate();
        this.userModif = entityInst.getUserModif();
        this.dateModif = entityInst.getDateModif();
    }


}
