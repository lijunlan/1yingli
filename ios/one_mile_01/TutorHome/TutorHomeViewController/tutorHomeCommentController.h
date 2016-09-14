//
//  tutorHomeCommentController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "tutorHomeCommentCell.h"
#import "tutorHomeCommentModal.h"
@interface tutorHomeCommentController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) NSString *DetialID;
@property (nonatomic, strong) UITableView *tutorHomeCommentTV;//学员评价
@property (nonatomic, strong) UIImageView *imageView1; //头像
@property (nonatomic, strong) NSMutableArray *tutorHomeCommentArray;//评价数组
@property (nonatomic, copy) NSString *photoURLForComments;
@property (nonatomic, assign) BOOL isUpLoading;
@property (assign, nonatomic) NSInteger nextPage;//上拉加载

@end
