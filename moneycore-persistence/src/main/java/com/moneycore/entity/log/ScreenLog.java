package com.moneycore.entity.log;

import com.moneycore.entity.Screen;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_screen")
public class ScreenLog {

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
    @Column(name = "screen_code")
    private String screenCode;
    @Column(name = "screen_id")
    private String screenId;
    @Column(name = "name")
    private String name;
    @Column(name = "wording")
    private String wording;
    @Column(name = "url")
    private String url;
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


    public ScreenLog(){
    }
    public ScreenLog(Screen entityInst, String action, String user){
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = user;
        // others
        this.screenCode = entityInst.getScreenCode();
        this.screenId = entityInst.getScreenId();
        this.name = entityInst.getName();
        this.wording = entityInst.getWording();
        this.url = entityInst.getUrl();
        this.userCreate = entityInst.getUserCreate();
        this.dateCreate = entityInst.getDateCreate();
        this.userModif = entityInst.getUserModif();
        this.dateModif = entityInst.getDateModif();
    }


}
