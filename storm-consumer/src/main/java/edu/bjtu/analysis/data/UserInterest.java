package edu.bjtu.analysis.data;


import edu.bjtu.analysis.topology.UserBehaviorTopology;

/**
 * @author baohehe
 * @date 2018/06/18
 *
 * 用户id	兴趣
 * Uid	InterestName
 * U0000001	javascript
 * U0000002	网络安全
 * U0000003	数据库
 */
public class UserInterest {

    private String uid;
    private String interestName;

    public UserInterest(String line) {
        String[] values = line.split(String.valueOf(UserBehaviorTopology.split));
        if (values.length != 2) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.uid = values[0];
        this.interestName = values[1];
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }
}
