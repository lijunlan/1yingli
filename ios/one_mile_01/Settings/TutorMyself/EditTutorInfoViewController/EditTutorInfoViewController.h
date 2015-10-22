//
//  EditTutorInfoViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "editTutorInfoTableViewCell.h"
#import "showMoreViewController.h"
#import "AFNConnect.h"
#import "editTutorInfoModel.h"
#import "editTutorAppraiseModel.h"
#import "JSONKit.h"
#import <UIImageView+WebCache.h>
#import "editTutorInfoSettingView.h"
#import "editTutorInfoModel.h"

#import "tutorCommentViewController.h"
@interface EditTutorInfoViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,cancelEditTutor>
@property (nonatomic,retain)UIScrollView *scrollView;

@property (nonatomic,strong)UIImageView *imageView;//头像
@property (nonatomic,strong)UILabel *nameLabel;//姓名
@property (nonatomic,strong)UILabel *label2; //介绍
@property (nonatomic,copy)NSString *toDetialID; //获得下一数据的teacherId
@property (nonatomic, strong) NSMutableArray *commentArray;//评价数组
@property (nonatomic, strong) editTutorInfoSettingView *editTutorInfoSettingV;



@end
