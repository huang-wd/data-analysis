package edu.bjtu.analysis;

/**
 * @author huangweidong
 * @date 2018/06/18
 */
public class StormConsumer {
    public final static char split = '\001';

    public static final String[][] fileDefinitions = {
            {"src/main/resources/数据仓库课程实验三数据/articleInfo/articleInfo", "edu.bjtu.analysis.data.ArticleInfo", "t_article_info"},
            {"src/main/resources/数据仓库课程实验三数据/userBasic/userBasic", "edu.bjtu.analysis.data.UserBasic", "t_user_basic"},
            {"src/main/resources/数据仓库课程实验三数据/userBehavior/userBehavior", "edu.bjtu.analysis.data.UserBehavior", "t_user_behavior"},
            {"src/main/resources/数据仓库课程实验三数据/userEdu/userEdu", "edu.bjtu.analysis.data.UserEdu", "t_user_edu"},
            {"src/main/resources/数据仓库课程实验三数据/userInterest/userInterest", "edu.bjtu.analysis.data.UserInterest", "t_user_interest"},
            {"src/main/resources/数据仓库课程实验三数据/userSkill/userSkill", "edu.bjtu.analysis.data.UserSkill", "t_user_skill"}
    };



    public static void main(String[] args) throws Exception {

    }




}
