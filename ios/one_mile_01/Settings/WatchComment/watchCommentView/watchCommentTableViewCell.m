//
//  watchCommentTableViewCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "watchCommentTableViewCell.h"

@implementation watchCommentTableViewCell


-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self creatCellView];
    }
    return  self;
}
-(void)creatCellView{
    
    self.bgImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0,[UIScreen mainScreen].bounds.size.width, 165)];
    self.bgImageView.image = [UIImage imageNamed:@"kuangjia.png"];
    self.bgImageView.layer.cornerRadius = 5;
    [self.contentView addSubview:self.bgImageView];
    
    self.contentLabel  = [[UILabel alloc]initWithFrame:CGRectMake(5, 10 , self.bgImageView.frame.size.width - 10 , 60)];
    self.contentLabel.textColor = [UIColor lightGrayColor];
    self.contentLabel.numberOfLines = 2;
//    self.contentLabel.backgroundColor = [UIColor yellowColor];
    [self.bgImageView addSubview:self.contentLabel];
    
    
    self.lineLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.contentLabel.frame.origin.x, self.contentLabel.frame.origin.y + self.contentLabel.frame.size.height + 5 , self.contentLabel.frame.size.width, 0.5)];
    self.lineLabel.backgroundColor = [UIColor lightGrayColor];
    [self.bgImageView addSubview:self.lineLabel];
    
    self.headImageView = [[UIImageView alloc]initWithFrame:CGRectMake(self.lineLabel.frame.origin.x, self.contentLabel.frame.origin.y + self.contentLabel.frame.size.height + 12, 55, 55)];
    self.headImageView.layer.masksToBounds = YES;
    self.headImageView.layer.cornerRadius = 27;
//    self.headImageView.backgroundColor = [UIColor redColor];
    [_bgImageView addSubview:self.headImageView];
    
    
    self.nameLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.headImageView.frame.origin.x + self.headImageView.frame.size.width + 10, self.headImageView.frame.origin.y, 100, 25)];
//    self.nameLabel.backgroundColor = [UIColor redColor];
    [_bgImageView addSubview:self.nameLabel];
    
    self.timeLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.nameLabel.frame.origin.x, self.nameLabel.frame.origin.y + self.nameLabel.frame.size.height + 5, 150, 25)];
    self.timeLabel.font = [UIFont systemFontOfSize:15];
//    self.timeLabel.backgroundColor = [UIColor redColor];
    self.timeLabel.textColor = [UIColor colorWithRed:204/255.0 green:204/255.0 blue:204/255.0 alpha:1.0];
    [_bgImageView addSubview:self.self.timeLabel];
    
    
  
}

-(void)setWatchCommentM:(watchCommentModel *)watchCommentM{

    self.watchCommentScore = watchCommentM.score;
    self.score = [self.watchCommentScore intValue];

    for (int i = 0; i < 5; i++) {
        
        self.startButton = [UIButton buttonWithType:UIButtonTypeCustom];
        
        self.startButton.frame = CGRectMake(self.bgImageView.frame.size.width - 150  + i * 25, self.timeLabel.frame.origin.y - 30, 25, 25);
        
        if (i < self.score) {
            
            [self.startButton setBackgroundImage:[UIImage imageNamed:@"comments_favor.png"] forState:UIControlStateNormal];
            
        }else{
            
            [self.startButton setBackgroundImage:[UIImage imageNamed:@"comments_dislike.png"] forState:UIControlStateNormal];
            
        }
        
        [_bgImageView addSubview:self.startButton];
    }
    
    
    self.timeLabel.text = watchCommentM.createTime;
    self.contentLabel.text = watchCommentM.content;
    [self.headImageView sd_setImageWithURL:[NSURL URLWithString:watchCommentM.url]];
    self.nameLabel.text = watchCommentM.name;
    self.titileLabel.text = watchCommentM.commentId;
    
}


@end
