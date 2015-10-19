//
//  SettingViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "settingTableViewCell.h"
#import "applyTutorViewController.h"
#import "EditTutorInfoViewController.h"
#import "aboutUsViewController.h"
#import "AccountSecurityViewController.h"
#import "watchCommentViewController.h"
#import "TagForClient.h"
@interface SettingViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>
@property (nonatomic,retain)UITableView *settingTableView;
@property (nonatomic,retain)UIImageView *imageView;
@property (nonatomic,retain)UILabel *nameLabel;
@property (nonatomic,assign)BOOL isFirst;
@end
