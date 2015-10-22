//
//  tutorHomeViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/8/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "appraiseTabelViewCell.h"
#import "talkTableViewCell.h"
#import "AppointmentOrderViewController.h"
#import "showMoreViewController.h"
#import "StudyPilotModel.h"
#import "collectionDataBase.h"
#import "LoginViewController.h"
#import "PowerModel.h"
#import "CampusModel.h"
#import "ShareModel.h"
#import "ApplyModel.h"
#import <Masonry.h>
#import "tutorHomeCommentController.h"

#import <ShareSDK/ShareSDK.h>
#import <ShareSDKConnector/ShareSDKConnector.h>

//腾讯SDK头文件
#import <TencentOpenAPI/TencentOAuth.h>
#import <TencentOpenAPI/QQApiInterface.h>

//微信SDK头文件
#import "WXApi.h"

//新浪微博SDK头文件
#import "WeiboSDK.h"

//人人SDK头文件
#import <RennSDK/RennSDK.h>

//GooglePlus SDK头文件
#import <GooglePlus/GooglePlus.h>

#import <ShareSDKExtension/SSEShareHelper.h>
#import <ShareSDKUI/ShareSDK+SSUI.h>
#import <ShareSDKUI/SSUIShareActionSheetStyle.h>
#import <ShareSDKUI/SSUIShareActionSheetCustomItem.h>
#import <ShareSDK/ShareSDK+Base.h>

@interface tutorHomeViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic,strong)UIScrollView *scrollView;
@property (nonatomic,strong)UIImageView *imageView;
@property (nonatomic,strong)UILabel *nameLabel;//姓名
@property (nonatomic,strong)UILabel *label2; //介绍1
@property (nonatomic,strong)UILabel *majorL; //介绍2
@property (nonatomic,strong)UITableView *tableView1;//学员评价
@property (nonatomic,strong)UILabel *label18;    //    学员评价
@property (nonatomic,copy)NSString *toDetialID; //获得下一数据的teacherId
@property (nonatomic,strong)UIButton *button3;//查看更多评价
@property (nonatomic,strong)UIImageView *imageView5;
@property (nonatomic,strong)UIButton *button4;
@property (nonatomic,strong)PowerModel *powerM;
@property (nonatomic,strong)CampusModel *campusM;
@property (nonatomic,strong)ApplyModel *applyM;
@property (nonatomic,strong)ShareModel *shareM;
@property (nonatomic,strong)UIButton *button1;
@property (nonatomic,copy)NSString *contentString;
//标题
@property (nonatomic, copy) NSString *serviceTitleStr;

//加载视图
@property (nonatomic, strong) UIActivityIndicatorView *loadingView;
//*  面板
@property (nonatomic, strong) UIView *mainView;
@property (nonatomic, strong) UIView *panelView;

@end
