//
//  OrderForTutorModel.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface OrderForTutorModel : NSObject

@property (nonatomic, copy) NSString *orderId;
@property (nonatomic, copy) NSString *createTime;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *price;
@property (nonatomic, copy) NSString *time;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *iconUrl;
@property (nonatomic, copy) NSString *state;
@property (nonatomic, copy) NSString *question;
@property (nonatomic, copy) NSString *userIntroduce;
@property (nonatomic, copy) NSString *selectTimes;
@property (nonatomic, copy) NSString *okTime;
@property (nonatomic, copy) NSString *email;
@property (nonatomic, copy) NSString *phone;

@end
