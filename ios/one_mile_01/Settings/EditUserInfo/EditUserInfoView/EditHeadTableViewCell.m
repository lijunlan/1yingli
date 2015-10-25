//
//  EditHeadTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditHeadTableViewCell.h"

@interface EditHeadTableViewCell ()
{
    UILabel *iconLabel;
    UILabel *aLine;
}

@end

@implementation EditHeadTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        self.downloadDic = [NSMutableDictionary dictionary];
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    iconLabel = [[UILabel alloc] initWithFrame:CGRectMake(30, 30, 80, 40)];
    //iconLabel.backgroundColor = [UIColor yellowColor];
    iconLabel.font = [UIFont systemFontOfSize:14.0f];
    iconLabel.text = @"头像";
    iconLabel.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    [self.contentView addSubview:iconLabel];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(iconLabel.frame.origin.x - 5, 100 - 2, WIDTH - (iconLabel.frame.origin.x - 5) * 2, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    self.headerIV = [[UIImageView alloc] initWithFrame:CGRectMake(WIDTH - 80 - 20, 10, 80, 80)];
    self.headerIV.layer.masksToBounds = YES;
    self.headerIV.userInteractionEnabled = YES;
    self.headerIV.layer.cornerRadius = 40;
    self.headerIV.image = [UIImage imageNamed:@"placeholders.png"];
    //self.headerIV.backgroundColor = [UIColor yellowColor];
    [self.contentView addSubview:_headerIV];
    
    UIGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAction:)];
    [self.headerIV addGestureRecognizer:tap];
}

-(void)tapAction:(UITapGestureRecognizer *)tap
{
    [self.myDelegate chooseImage];
}

-(void)setDownloadDic:(NSMutableDictionary *)downloadDic
{
    NSString *url = [[downloadDic objectForKey:@"url"] substringWithRange:NSMakeRange(0, ((NSString *)[downloadDic objectForKey:@"url"]).length - 6)];
    
    [self.headerIV sd_setImageWithURL:[NSURL URLWithString:url] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    /*
    if (url.length != 0) {
        
        NSMutableDictionary *dic = [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"];
        [dic setValue:url forKey:@"iconUrl"];
        [[NSUserDefaults standardUserDefaults] setValue:[NSDictionary dictionaryWithDictionary:dic] forKey:@"userInfo"];
    }*/
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}

@end
