//
//  tutorHomeCommentCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorHomeCommentCell.h"

@implementation tutorHomeCommentCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self creatCellView];
    }
    return  self;
}
-(void)creatCellView{
    
    self.bgImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0,[UIScreen mainScreen].bounds.size.width, 165)];
    self.bgImageView.backgroundColor = [UIColor colorWithRed:232/255.0 green:234/255.0 blue:235/255.0 alpha:1.0];
    self.bgImageView.image = [UIImage imageNamed:@"kuangjia.png"];
    self.bgImageView.layer.cornerRadius = 5;
    [self.contentView addSubview:self.bgImageView];
    
    self.label1 = [[UILabel alloc]initWithFrame:CGRectMake(self.bgImageView.frame.origin.x + 15 , self.bgImageView.frame.origin.y + 25, self.bgImageView.frame.size.width - 25, 40)];
    self.label1.font = [UIFont systemFontOfSize:15];
    self.label1.numberOfLines = 2;
//        self.label1.backgroundColor = [UIColor redColor];
    [self.contentView addSubview:self.label1];
    
    self.label2 = [[UILabel alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x , self.label1.frame.origin.y + self.label1.frame.size.height + 5, self.label1.frame.size.width, 30)];
    self.label2.font = [UIFont systemFontOfSize:13];
    //    self.label2.backgroundColor = [UIColor redColor];
    self.label2.textColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1.0];
    [self.contentView addSubview:self.label2];
    
    self.linelebel = [[UILabel alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x , self.label2.frame.origin.y + self.label2.frame.size.height + 3, self.bgImageView.frame.size.width - 30, 0.5)];
    self.linelebel.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:self.linelebel];
    
    self.label5 = [[UILabel alloc]initWithFrame:CGRectMake(self.label2.frame.origin.x + 5 , self.label2.frame.origin.y + self.label2.frame.size.height + 5, self.label2.frame.size.width - 10, 0.5)];
    //    self.label5.backgroundColor = [UIColor blackColor];
    [self.contentView addSubview:self.label5];
    
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

//赋值 and 自动换行,计算出cell的高度
-(void)setlabel2Text:(NSString *)text{
    //获得当前cell高度
    CGRect frame = [self frame];
    //文本赋值
    self.label1.text = text;
    //设置label的最大行数
    self.label1.numberOfLines = 10;
    CGSize size = CGSizeMake(WIDTH - 35, 1000);
    
    CGSize labelSize = [self.label1.text sizeWithFont:self.label1.font constrainedToSize:size lineBreakMode:NSLineBreakByClipping];
    
    self.label1.frame = CGRectMake(self.label1.frame.origin.x, self.label1.frame.origin.y, labelSize.width, labelSize.height);
    
    self.label2.frame = CGRectMake(self.label1.frame.origin.x , self.label1.frame.origin.y + self.label1.frame.size.height + 5,WIDTH - 35, 30);
    
    self.linelebel.frame = CGRectMake(self.label1.frame.origin.x , self.label2.frame.origin.y + self.label2.frame.size.height + 3, self.bgImageView.frame.size.width - 30, 0.5);
    
    self.label5.frame = CGRectMake(self.label2.frame.origin.x + 5 , self.label2.frame.origin.y + self.label2.frame.size.height + 5, self.label2.frame.size.width - 10, 0.5);
    
    self.imageView1.frame = CGRectMake(self.label5.frame.origin.x - 3, self.label5.frame.origin.y + 3,50 , 50);
    
    self.label3.frame  = CGRectMake(self.imageView1.frame.origin.x + self.imageView1.frame.size.width + 8 , self.label5.frame.origin.y + 5,200,25);

    self.label4.frame = CGRectMake(self.label3.frame.origin.x , self.label3.frame.origin.y + self.label3.frame.size.height + 2,250,20);

     self.bgImageView.frame = CGRectMake(0, 0,[UIScreen mainScreen].bounds.size.width, self.label4.frame.origin.y + 40);

    
    //计算出自适应的高度
    frame.size.height = self.label4.frame.origin.y + 40;
    self.frame = frame;
}


@end
