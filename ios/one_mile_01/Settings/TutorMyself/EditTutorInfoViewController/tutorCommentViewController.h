//
//  tutorCommentViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "tutorCommentViewController.h"
#import "tutorCommentCell.h"
#import "tutorCommentModel.h"
@interface tutorCommentViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) NSString *DetialID;
@property (nonatomic, strong) UITableView *tableView1;//学员评价
@property (nonatomic, strong) UIImageView *imageView1; //头像
@property (nonatomic, strong) NSMutableArray *commentArray;//评价数组
@property (nonatomic, copy) NSString *photoURLForComments;
@property (nonatomic, assign) BOOL isUpLoading;


@end
