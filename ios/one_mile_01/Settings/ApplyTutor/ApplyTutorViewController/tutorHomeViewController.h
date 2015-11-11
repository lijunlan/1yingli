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
@interface tutorHomeViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>
@property (nonatomic,retain)UIScrollView *scrollView;
@property (nonatomic,retain)UIImageView *imageView;
@property (nonatomic,retain)UILabel *nameLabel;//姓名
@property (nonatomic,retain)UILabel *label2; //介绍
@property (nonatomic,retain)UITableView *tableView1;//学员评价
@property (nonatomic,retain)UITableView *tableView2;//相关话题


@end
