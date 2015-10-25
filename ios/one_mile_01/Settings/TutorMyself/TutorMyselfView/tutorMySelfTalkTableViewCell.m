//
//  tutorMySelfTalkTableViewCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorMySelfTalkTableViewCell.h"

@implementation tutorMySelfTalkTableViewCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self creatCellView];
    }
    return  self;
}
-(void)creatCellView{
    UIImageView *bgImageView = [[UIImageView alloc] initWithFrame:CGRectMake(5, 5,[UIScreen mainScreen].bounds.size.width - 30, 150 - 10)];
    bgImageView.backgroundColor = [UIColor colorWithRed:232/255.0 green:234/255.0 blue:235/255.0 alpha:1.0];
    bgImageView.layer.cornerRadius = 5;
    [self.contentView addSubview:bgImageView];
    
    self.imageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(bgImageView.frame.origin.x + 20, self.imageView1.frame.origin.y + 20,80 , 80)];
    self.imageView1.backgroundColor = [UIColor redColor];
    self.imageView1.layer.cornerRadius = 40;
    [self addSubview:self.imageView1];
    
    self.label1 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView1.frame.origin.x + self.imageView1.frame.size.width + 10 , bgImageView.frame.origin.y + 20, bgImageView.frame.size.width - 30 - self.imageView1.frame.size.width, 30)];
    self.label1.backgroundColor = [UIColor redColor];
    [self.contentView addSubview:self.label1];
    
    self.label2 = [[UILabel alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x , self.label1.frame.origin.y + self.label1.frame.size.height + 10, 200, 25)];
    self.label2.text = @"   房产投资专家顾问五年";
    self.label2.textColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    self.label2.layer.cornerRadius = 13;
    self.label2.layer.borderWidth = 1;
    self.label2.layer.borderColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0].CGColor;
    self.label2.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:self.label2];
    
    self.label3 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView1.frame.origin.x + 5, self.imageView1.frame.origin.y + self.imageView1.frame.size.height + 5, self.imageView1.frame.size.width, 30)];
    self.label3.backgroundColor = [UIColor redColor];
    [self.contentView addSubview:self.label3];
    
    UIImageView *imageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(self.label3.frame.origin.x + 220, self.label3.frame.origin.y, 20, 20)];
    imageView2.backgroundColor = [UIColor clearColor];
    imageView2.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:imageView2];
    
    //    见过的人数
    self.label4 = [[UILabel alloc]initWithFrame:CGRectMake(imageView2.frame.origin.x + imageView2.frame.size.width + 5, imageView2.frame.origin.y + 5,15, 10)];
    self.label4.text = @"12";
    self.label4.font = [UIFont systemFontOfSize:13];
    self.label4.textColor = [UIColor colorWithRed:106/255.0 green:107/255.0 blue:108/255.0 alpha:1.0];
    self.label4.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:self.label4];
    
    self.label5 = [[UILabel alloc]initWithFrame:CGRectMake(self.label4.frame.origin.x + self.label4.frame.size.width + 5, self.label4.frame.origin.y ,100, 10)];
    self.label5.font = [UIFont systemFontOfSize:13];
    self.label5.textColor = [UIColor colorWithRed:106/255.0 green:107/255.0 blue:108/255.0 alpha:1.0];
    self.label5.text = @"人见过";
    self.label5.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:self.label5];
    
    
}


@end
