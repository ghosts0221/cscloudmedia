const menu = {
    list() {
        return [{
            "backMenu": [{
                "child": [{
                    "appFrontIcon": "cuIcon-pay",
                    "buttons": ["新增", "查看", "Revise", "删除"],
                    "menu": "User",
                    "menuJump": "列表",
                    "tableName": "yonghu"
                }], "menu": "用户管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-medal",
                    "buttons": ["新增", "查看", "Revise", "删除"],
                    "menu": "文章类型",
                    "menuJump": "列表",
                    "tableName": "wenzhangleixing"
                }], "menu": "文章类型管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-discover",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论", "审核"],
                    "menu": "Article",
                    "menuJump": "列表",
                    "tableName": "wenzhangxinxi"
                }], "menu": "ArticleInformation管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-vip",
                    "buttons": ["新增", "查看", "Revise", "删除"],
                    "menu": "图片类型",
                    "menuJump": "列表",
                    "tableName": "tupianleixing"
                }], "menu": "图片类型管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-cardboard",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论", "审核"],
                    "menu": "Image",
                    "menuJump": "列表",
                    "tableName": "tupianxinxi"
                }], "menu": "图片信息管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-album",
                    "buttons": ["新增", "查看", "Revise", "删除"],
                    "menu": "视频类型",
                    "menuJump": "列表",
                    "tableName": "shipinleixing"
                }], "menu": "视频类型管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-brand",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论", "审核"],
                    "menu": "Multimedia",
                    "menuJump": "列表",
                    "tableName": "shipinxinxi"
                }], "menu": "视频信息管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-similar",
                    "buttons": ["新增", "查看", "Revise", "删除"],
                    "menu": "轮播图管理",
                    "tableName": "config"
                }], "menu": "系统管理"
            }],
            "frontMenu": [{
                "child": [{
                    "appFrontIcon": "cuIcon-newshot",
                    "buttons": ["查看"],
                    "menu": "文章信息列表",
                    "menuJump": "列表",
                    "tableName": "wenzhangxinxi"
                }], "menu": "文章信息模块"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-news",
                    "buttons": ["查看"],
                    "menu": "图片信息列表",
                    "menuJump": "列表",
                    "tableName": "tupianxinxi"
                }], "menu": "图片信息模块"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-present",
                    "buttons": ["查看"],
                    "menu": "视频信息列表",
                    "menuJump": "列表",
                    "tableName": "shipinxinxi"
                }], "menu": "视频信息模块"
            }],
            "hasBackLogin": "是",
            "hasBackRegister": "否",
            "hasFrontLogin": "否",
            "hasFrontRegister": "否",
            "roleName": "Administrator",
            "tableName": "users"
        }, {
            "backMenu": [{
                "child": [{
                    "appFrontIcon": "cuIcon-discover",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论"],
                    "menu": "Article",
                    "menuJump": "列表",
                    "tableName": "wenzhangxinxi"
                }], "menu": "文章信息管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-cardboard",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论"],
                    "menu": "Image",
                    "menuJump": "列表",
                    "tableName": "tupianxinxi"
                }], "menu": "图片信息管理"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-brand",
                    "buttons": ["新增", "查看", "Revise", "删除", "查看评论"],
                    "menu": "Multimedia",
                    "menuJump": "列表",
                    "tableName": "shipinxinxi"
                }], "menu": "视频信息管理"
            }],
            "frontMenu": [{
                "child": [{
                    "appFrontIcon": "cuIcon-newshot",
                    "buttons": ["查看"],
                    "menu": "文章信息列表",
                    "menuJump": "列表",
                    "tableName": "wenzhangxinxi"
                }], "menu": "文章信息模块"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-news",
                    "buttons": ["查看"],
                    "menu": "图片信息列表",
                    "menuJump": "列表",
                    "tableName": "tupianxinxi"
                }], "menu": "图片信息模块"
            }, {
                "child": [{
                    "appFrontIcon": "cuIcon-present",
                    "buttons": ["查看"],
                    "menu": "视频信息列表",
                    "menuJump": "列表",
                    "tableName": "shipinxinxi"
                }], "menu": "视频信息模块"
            }],
            "hasBackLogin": "否",
            "hasBackRegister": "否",
            "hasFrontLogin": "是",
            "hasFrontRegister": "是",
            "roleName": "User",
            "tableName": "yonghu"
        }]
    }
}
export default menu;
