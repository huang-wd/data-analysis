package edu.bjtu.analysis.data;


/**
 * @author baohehe
 * @date 2018/06/18
 * <p>
 * 用户id	技能
 * Uid	SkillName
 * U0000001	C#
 * U0000002	django
 * U0000003	extjs
 */
public class UserSkill {
    private String uid;
    private String skillName;

    public UserSkill(String line) {
        String[] values = line.split(String.valueOf(""));
        if (values.length != 2) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.uid = values[0];
        this.skillName = values[1];
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
