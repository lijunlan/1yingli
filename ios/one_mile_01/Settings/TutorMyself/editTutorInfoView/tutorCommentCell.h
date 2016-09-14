//
//  tutorCommentCell.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface tutorCommentCell : UITableViewCell
@property (nonatomic,retain)UILabel *label1; //评价
@property (nonatomic,retain)UILabel *label2;//参与评价
@property (nonatomic,retain)UILabel *label3;//姓名
@property (nonatomic,retain)UILabel *label4;//时间
@property (nonatomic,retain)UIImageView *imageView1; //头像
@property (nonatomic,retain)UILabel *label5;//分割线

@property (nonatomic,strong)UIImageView *bgImageView;
@property (nonatomic,strong)UILabel *linelebel ;

-(void)setlabel2Text:(NSString*)text;

@end
