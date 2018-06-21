package edu.bjtu.analysis.data;

/**
 * @author baohehe
 * @date 2018/06/18
 * <p>
 * 用户id	行为类型	文章id	行为时间
 * Uid	Behavior	Aid	BehaviorTime
 * U0000001	0	D0000001	2009-04-01 00:00:00
 * U0000002	1	D0000002	2002-08-01 00:00:00
 * U0000003	2	D0000003	2014-07-01 00:00:00
 * <p>
 * <p>
 * 说明：
 * 1, Behavior字段值代表的意思：
 * 0:发表文章
 * 1:浏览文章
 * 2:评论文章
 */
public class UserBehavior {
    private String uid;
    private Integer behavior;
    private String aid;
    private String behaviorTime;

    public UserBehavior(String line) {
        String[] values = line.split(String.valueOf(""));
        if (values.length != 4) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.uid = values[0];
        this.behavior = Integer.valueOf(values[1]);
        this.aid = values[2];
        this.behaviorTime = values[3];
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getBehavior() {
        return behavior;
    }

    public void setBehavior(Integer behavior) {
        this.behavior = behavior;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBehaviorTime() {
        return behaviorTime;
    }

    public void setBehaviorTime(String behaviorTime) {
        this.behaviorTime = behaviorTime;
    }
}
