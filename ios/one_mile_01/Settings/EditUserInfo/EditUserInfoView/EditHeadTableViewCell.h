//
//  EditHeadTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol editHeaderInterface <NSObject>

@optional
-(void)chooseImage;

@end

@interface EditHeadTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *headerIV;
@property (nonatomic, strong) NSMutableDictionary *downloadDic;

@property (nonatomic, assign) id<editHeaderInterface>myDelegate;

@end
