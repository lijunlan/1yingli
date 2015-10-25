//
//  QueryProduct.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/14.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QueryProduct : NSObject

@property (nonatomic, assign) float price;
@property (nonatomic, strong) NSString *subject;
@property (nonatomic, strong) NSString *body;
@property (nonatomic, strong) NSString *orderId;

@end
