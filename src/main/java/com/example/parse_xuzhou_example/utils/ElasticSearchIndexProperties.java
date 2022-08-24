package com.example.parse_xuzhou_example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GaoLiuKai
 * @date 2022/8/5 15:33
 */
public final class ElasticSearchIndexProperties {
    // 新建索引的 Settings
    public static final Map<String, Object> indexSettingsMap = new HashMap<>() {
        {
            put("index.number_of_replicas", 1);
            put("index.number_of_shards", 3);

            put("index.analysis", new HashMap<>() {
                {
                    put("filter", new HashMap<>() {
                        {
                            put("my_stopwords", new HashMap<>() {
                                {
                                    put("type", "stop");
                                }
                            });
                        }
                    });
                    put("analyzer", new HashMap<>() {
                        {
                            put("max_analyzer", new HashMap<>() {
                                {
                                    put("type", "custom");
                                    put("char_filter", new ArrayList<String>() {
                                        {
                                            add("html_strip");
                                        }
                                    });
                                    put("tokenizer", "ik_max_word");
                                }
                            });
                            put("smart_analyzer", new HashMap<>() {
                                {
                                    put("type", "custom");
                                    put("char_filter", new ArrayList<String>() {
                                        {
                                            add("html_strip");
                                        }
                                    });
                                    put("tokenizer", "ik_max_word");
                                }
                            });
                        }
                    });
                }
            });
        }
    };
    // 新建索引的mappings( 不带doc字段 )
    public static final String mappings = "{\n" +
            "        \"properties\": {\n" +
            "          \"caseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"caseType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"causes\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +

//            "          \"causesIsMatched\": {\n" +
//            "            \"type\": \"boolean\"\n" +
//            "          },\n" +

            "          \"city\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"claims\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"content\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"court\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtConsider\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"createTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"decideTime\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"decideTimeYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"deleted\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"docId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"docType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"errors\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"evidences\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\",\n" +
            "                \"ignore_above\": 256\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"fields\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"fieldId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"fieldName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"rootId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"value\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"focusOfControversy\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"forSearch\": {\n" +
            "            \"properties\": {\n" +
            "              \"causes\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"court\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"historyCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"historyId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"insertTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          },\n" +
            "          \"judge\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgeGist\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementCategory\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementResult\": {\n" +
            "            \"properties\": {\n" +
            "              \"desc\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"properties\": {\n" +
            "                  \"剥夺政治权利\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"当事人\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"徒刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"拘役\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"死刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"没收财产\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"管制\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"缓刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罚金\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名是否成立\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"全部支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部驳回\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"发回重审\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"维持原判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgementTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd\"\n" +
            "          },\n" +
            "          \"lastUpdateTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"lawInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"clauses\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"lawName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"law_category\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"laws\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"lawyerInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 50\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawyerName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationParticipant\": {\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"isLawyer\": {\n" +
            "                \"type\": \"boolean\"\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationText\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paraTags\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paras\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"content\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"tag\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"party\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"age\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"career\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"country\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"defender\": {\n" +
            "                \"type\": \"nested\",\n" +
            "                \"properties\": {\n" +
            "                  \"lawFirmName\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"name\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"guardian\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"industry\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"previousTrialCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"province\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"rejectReason\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"repeated\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"sensitiveInfo\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"subTrialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"topCause\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trailForm\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialProcedure\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"uniqueId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"unique_id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"updateTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          }\n" +
            "        }\n" +
            "      }";

    public static final String Mappings_causesIsMatched =  "{\n" +
            "        \"properties\": {\n" +
            "          \"caseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"caseType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"causes\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"causesIsMatched\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"city\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"claims\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"content\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"court\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtConsider\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"createTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"decideTime\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"decideTimeYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"deleted\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"docId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"docType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"errors\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"evidences\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\",\n" +
            "                \"ignore_above\": 256\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"fields\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"fieldId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"fieldName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"rootId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"value\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"focusOfControversy\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"forSearch\": {\n" +
            "            \"properties\": {\n" +
            "              \"causes\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"court\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"historyCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"historyId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"isInsertedDevEs\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"insertTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          },\n" +
            "          \"judge\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgeGist\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementCategory\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementResult\": {\n" +
            "            \"properties\": {\n" +
            "              \"desc\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"properties\": {\n" +
            "                  \"剥夺政治权利\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"当事人\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"徒刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"拘役\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"死刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"没收财产\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"管制\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"缓刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罚金\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名是否成立\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"全部支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部驳回\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"发回重审\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"维持原判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgementTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd\"\n" +
            "          },\n" +
            "          \"lastUpdateTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"lawInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"clauses\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"lawName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"law_category\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"laws\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"lawyerInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 50\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawyerName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationParticipant\": {\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"isLawyer\": {\n" +
            "                \"type\": \"boolean\"\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationText\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paraTags\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paras\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"content\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"tag\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"party\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"age\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"career\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"country\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"defender\": {\n" +
            "                \"type\": \"nested\",\n" +
            "                \"properties\": {\n" +
            "                  \"lawFirmName\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"name\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"guardian\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"industry\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"previousTrialCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"province\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"rejectReason\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"repeated\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"sensitiveInfo\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"subTrialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"topCause\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trailForm\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialProcedure\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"uniqueId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"unique_id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"updateTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          }\n" +
            "        }\n" +
            "      }";

    // 新建索引的mappings （ 带doc字段 ）
    public static final String mappings_doc_causes_is_matched = "{\n" +
            "      \"_doc\": {\n" +
            "        \"properties\": {\n" +
            "          \"caseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"caseNumberYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"caseType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"causes\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"causesIsMatched\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"city\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"claims\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"content\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"court\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtConsider\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"courtType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"createTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"decideTime\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"decideTimeYear\": {\n" +
            "            \"type\": \"integer\"\n" +
            "          },\n" +
            "          \"deleted\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"docId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"docType\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"errors\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"evidences\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\",\n" +
            "                \"ignore_above\": 256\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"fields\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"fieldId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"fieldName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"rootId\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"value\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"focusOfControversy\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"forSearch\": {\n" +
            "            \"properties\": {\n" +
            "              \"causes\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"court\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"historyCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"historyId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"insertTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          },\n" +
            "          \"judge\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgeGist\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementCategory\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"judgementResult\": {\n" +
            "            \"properties\": {\n" +
            "              \"desc\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"properties\": {\n" +
            "                  \"剥夺政治权利\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"当事人\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"徒刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"拘役\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"死刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"没收财产\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"管制\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"缓刑\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罚金\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"罪名是否成立\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"全部支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"全部驳回\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"发回重审\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"维持原判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分支持\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"部分改判\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"judgementTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd\"\n" +
            "          },\n" +
            "          \"lastUpdateTime\": {\n" +
            "            \"type\": \"date\"\n" +
            "          },\n" +
            "          \"lawInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"clauses\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"detail\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"lawName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"law_category\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"laws\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"lawyerInfo\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 50\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawyerName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationParticipant\": {\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"isLawyer\": {\n" +
            "                \"type\": \"boolean\"\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawFirmName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"lawyerRepresent\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"litigationText\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paraTags\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"paras\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"content\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"max\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"max_analyzer\"\n" +
            "                  },\n" +
            "                  \"smart\": {\n" +
            "                    \"type\": \"text\",\n" +
            "                    \"analyzer\": \"smart_analyzer\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"tag\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"party\": {\n" +
            "            \"type\": \"nested\",\n" +
            "            \"properties\": {\n" +
            "              \"address\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"age\": {\n" +
            "                \"type\": \"integer\"\n" +
            "              },\n" +
            "              \"anonymousName\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"birth\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"career\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"census_register\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"city\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"country\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"defender\": {\n" +
            "                \"type\": \"nested\",\n" +
            "                \"properties\": {\n" +
            "                  \"lawFirmName\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  },\n" +
            "                  \"name\": {\n" +
            "                    \"type\": \"keyword\"\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"district\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"education\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"guardian\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"industry\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"fields\": {\n" +
            "                  \"keyword\": {\n" +
            "                    \"type\": \"keyword\",\n" +
            "                    \"ignore_above\": 256\n" +
            "                  }\n" +
            "                }\n" +
            "              },\n" +
            "              \"lawAgentType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"litigationParticipantType\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"name\": {\n" +
            "                \"type\": \"text\"\n" +
            "              },\n" +
            "              \"nation\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"province\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"sex\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"street\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              },\n" +
            "              \"type\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"previousTrialCaseNumber\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"province\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"rejectReason\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"repeated\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "          },\n" +
            "          \"sensitiveInfo\": {\n" +
            "            \"type\": \"text\"\n" +
            "          },\n" +
            "          \"subTrialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"max\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"max_analyzer\"\n" +
            "              },\n" +
            "              \"smart\": {\n" +
            "                \"type\": \"text\",\n" +
            "                \"analyzer\": \"smart_analyzer\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"topCause\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trailForm\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialProcedure\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"trialRound\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"uniqueId\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"unique_id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          },\n" +
            "          \"updateTime\": {\n" +
            "            \"type\": \"date\",\n" +
            "            \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }" ;
}
