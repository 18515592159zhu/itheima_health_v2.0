package com.itheima.utils.impl;

import com.itheima.pojo.Question;
import com.itheima.pojo.QuestionnaireResult;
import com.itheima.utils.QuestionnaireCalculate;

import java.util.Date;
import java.util.List;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/27  11:05
 */
public class QuestionnaireCalculateImpl implements QuestionnaireCalculate {


    @Override
    public QuestionnaireResult calculate(List<Question> list) {
        int pinghe_Score = 0;
        int pinghe_Count = 0;
        int qixu_Score = 0;
        int qixu_Count = 0;
        int yangxu_Score = 0;
        int yangxu_Count = 0;
        int yinxu_Score = 0;
        int yinxu_Count = 0;
        int tanshi_Score = 0;
        int tanshi_Count = 0;
        int shire_Score = 0;
        int shire_Count = 0;
        int xueyu_Score = 0;
        int xueyu_Count = 0;
        int qiyu_Score = 0;
        int qiyu_Count = 0;
        int tebing_Score = 0;
        int tebing_Count = 0;
        /*
        计分方法:
        回答中医体质分类判定标准中的问题, 每一问题按5 级评分, 计算原始分及转化分, 依标准判定体质类型。
        每个条目原始最低分是1 分, 最高分是5 分, 9 个亚量表分别计算分数。先
        计算各亚量表的原始分数, 即原始分数= 各个条目分值相加。计算原始分数后再换算为转化分数, 各亚量表的转化分数为1~ 100 分。
        List<String, Map<>>
        数组形式存储数据呢? array[] 数组里类型是
        条目数就是数组的长度
        转化分数 =( 原始分- 条目数)÷( 条目数× 4)×100

        注：标有*的条目需先逆向计分，即：1→5，2→4，3→3，4→2，5→1，再用公式转化分。
        6-value * reversal

        如何计算9种分数,并(存储起来),题目本身是有顺序的
	    数组? a1存第一类型的分数..依次类推?,给初始值0

	    值, 是否反转, 9种子类型其中一种
         */
        for (Question question : list) {
            String subType = question.getSubType();
            switch (subType) {
                case "阳虚体质":
                    yangxu_Count++;
                    if(question.getReversal() == 0) {
                        yangxu_Score += question.getValue();
                    } else {
                        yangxu_Score += (6 - question.getValue());
                    }
                case "阴虚体质":
                    yinxu_Count++;
                    if(question.getReversal() == 0) {
                        yinxu_Score += question.getValue();
                    } else {
                        yinxu_Score += (6 - question.getValue());
                    }
                case "气虚体质":
                    qixu_Count++;
                    if(question.getReversal() == 0) {
                        qixu_Score += question.getValue();
                    } else {
                        qixu_Score += (6 - question.getValue());
                    }
                case "痰湿体质":
                    tanshi_Count++;
                    if(question.getReversal() == 0) {
                        tanshi_Score += question.getValue();
                    } else {
                        tanshi_Score += (6 - question.getValue());
                    }
                case "湿热体质":
                    shire_Count++;
                    if(question.getReversal() == 0) {
                        shire_Score += question.getValue();
                    } else {
                        shire_Score += (6 - question.getValue());
                    }
                case "血瘀体质":
                    xueyu_Count++;
                    if(question.getReversal() == 0) {
                        xueyu_Score += question.getValue();
                    } else {
                        xueyu_Score += (6 - question.getValue());
                    }
                case "特禀体质":
                    tebing_Count++;
                    if(question.getReversal() == 0) {
                        tebing_Score += question.getValue();
                    } else {
                        tebing_Score += (6 - question.getValue());
                    }
                case "气郁型":
                    qiyu_Count++;
                    if(question.getReversal() == 0) {
                        qiyu_Score += question.getValue();
                    } else {
                        qiyu_Score += (6 - question.getValue());
                    }
                case "平和质":
                    pinghe_Count++;
                    if(question.getReversal() == 0) {
                        pinghe_Score += question.getValue();
                    } else {
                        pinghe_Score += (6 - question.getValue());
                    }
            }
        }
        // 转化分数  转化分数 =( 原始分- 条目数)÷( 条目数× 4)×100
        yangxu_Score = getScore(yangxu_Score, yangxu_Count);
        yinxu_Score = getScore(yangxu_Score, yangxu_Count);
        qixu_Score = getScore(yangxu_Score, yangxu_Count);
        tanshi_Score = getScore(yangxu_Score, yangxu_Count);
        shire_Score = getScore(yangxu_Score, yangxu_Count);
        xueyu_Score = getScore(yangxu_Score, yangxu_Count);
        tebing_Score = getScore(yangxu_Score, yangxu_Count);
        qiyu_Score = getScore(yangxu_Score, yangxu_Count);
        pinghe_Score = getScore(yangxu_Score, yangxu_Count);
        // 判断体质
        /*
         平和质为正常体质，其他8种体质为偏颇体质。判定标准见下表。
          体质类型	条件	判定结果
        平和质	平和体质转化分≧60分	是
        	其他8种体质转化分均﹤30分	是
        	平和体质转化分≧40分	基本是
        	其他8种体质转化分均﹤40分	基本是
        	不满足上述条件者	否
        偏颇体质
  	    转化分≧40分	是
	    转化分30~39分	倾向是
	    转化分﹤30分	否
         */
        // 模拟计算!~!~~~~~~~~~~~~~~~~~~~
        return new QuestionnaireResult("2019070112314",new Date(),0,1,0,0,0,0,0,0,0);
    }

    private int getScore(int score, int count) {
        return (score - count) / (count * 4) * 100;

    }

}
