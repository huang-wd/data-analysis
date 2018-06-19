package edu.bjtu.analysis.data;


/**
 * @author huangweidong
 * @date 2018/06/18
 * <p>
 * 用户id	性别	状态	用户等级
 * Uid	Gender	Status	Level
 * U0000001	1	1	专家
 * U0000002	2	1	精英
 * U0000003	1	0	小白
 * <p>
 * <p>
 * 说明：
 * 1, Status字段值代表的意思：
 * 0：初始
 * 1：通过审核
 * 2：锁定		锁定含义：可能密码连续输入错误
 * 4：封杀		封杀：拉入系统黑名单
 * <p>
 * 2, Gender字段值代表的意思：
 * 1：男
 * 0：女
 * <p>
 * 3, Level字段值代表的意思：
 * 专家
 * 精英
 * 普通
 * 小白
 */
public class UserBasic {
    private String uid;
    private Integer gender;
    private Integer status;
    private String level;

    public UserBasic(String line) {
        String[] values = line.split(String.valueOf(""));
        if (values.length != 4) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.uid = values[0];
        this.gender = Integer.valueOf(values[1]);
        this.status = Integer.valueOf(values[2]);
        this.level = values[3];
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
