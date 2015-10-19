//
//  appraiseTabelViewCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/25.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "appraiseTabelViewCell.h"

@implementation appraiseTabelViewCell

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
    
    
    self.label1 = [[UILabel alloc]initWithFrame:CGRectMake(bgImageView.frame.origin.x + 5 , bgImageView.frame.origin.y + 5, bgImageView.frame.size.width - 10, 40)];
    self.label1.font = [UIFont systemFontOfSize:15];
    self.label1.numberOfLines = 2;
//    self.label1.backgroundColor = [UIColor redColor];
    [self.contentView addSubview:self.label1];
    
    self.label2 = [[UILabel alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x , self.label1.frame.origin.y + self.label1.frame.size.height + 5, self.label1.frame.size.width, 30)];
//    self.label2.backgroundColor = [UIColor redColor];
    self.label2.font = [UIFont systemFontOfSize:13];
    self.label2.textColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1.0];
    [self.contentView addSubview:self.label2];

    self.label5 = [[UILabel alloc]initWithFrame:CGRectMake(self.label2.frame.origin.x + 5 , self.label2.frame.origin.y + self.label2.frame.size.height + 5, self.label2.frame.size.width - 10, 0.5)];
//    self.label5.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:self.label5];

    UILabel *linelebel = [[UILabel alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x - 3, self.label2.frame.origin.y + self.label2.frame.size.height + 5, bgImageView.frame.size.width - 6, 0.5)];
    linelebel.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:linelebel];
    
    self.imageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(self.label5.frame.origin.x - 3, self.label5.frame.origin.y + 3,50 , 50)];
    self.imageView1.layer.masksToBounds = YES;
//    self.imageView1.backgroundColor = [UIColor redColor];
    self.imageView1.layer.cornerRadius = 24;
    [self addSubview:self.imageView1];
    
    
    self.label3 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView1.frame.origin.x + self.imageView1.frame.size.width + 8 , self.label5.frame.origin.y + 5,200,25)];
//    self.label3.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:self.label3];

    
    self.label4 = [[UILabel alloc]initWithFrame:CGRectMake(self.label3.frame.origin.x , self.label3.frame.origin.y + self.label3.frame.size.height + 2,250,20)];
    self.label4.textColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1.0];
//    self.label4.backgroundColor = [UIColor purpleColor];
    [self.contentView addSubview:self.label4];

}



@end
