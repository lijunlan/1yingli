//
//  collectionDataBase.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/7.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "collectionDataBase.h"
#import "FMDatabaseQueue.h"
#import "FMDatabase.h"
@interface collectionDataBase ()

@property (nonatomic, retain) FMDatabaseQueue *queue;

@end

@implementation collectionDataBase

SingletonM(collectionDataBase);
- (instancetype)init
{
    self = [super init];
    if (self) {
        NSString *file = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) lastObject];
        
        NSLog(@"%@", file);
        file = [file stringByAppendingPathComponent:@"collection.sqlite"];
        
        self.queue = [FMDatabaseQueue databaseQueueWithPath:file];
        
        [self.queue inDatabase:^(FMDatabase *db) {
            
            BOOL flag = [db executeUpdate:@"create table if not exists t_Collection (number INTEGER PRIMARY KEY AUTOINCREMENT, position TEXT, iconUrl TEXT, level TEXT, likeNo TEXT, name TEXT, serviceContent TEXT, serviceTitle TEXT, teacherId TEXT, isCollection TEXT);"];
            if (flag) {
                NSLog(@"创建成功");
            } else {
                NSLog(@"创建失败");
            }
        }];
    }
    return self;
}

-(void)insertStudyPilotModel:(StudyPilotModel *)StudyPilotModel{
    [self.queue inDatabase:^(FMDatabase *db) {
        
        BOOL flag = [db executeUpdate:@"insert into t_Collection (position, iconUrl, level, likeNo, name, serviceContent, serviceTitle, teacherId) values (?, ?, ?, ?, ?, ?, ?, ?);",StudyPilotModel.position,StudyPilotModel.iconUrl,StudyPilotModel.level,StudyPilotModel.likeNo,StudyPilotModel.name,StudyPilotModel.serviceContent,StudyPilotModel.serviceTitle,StudyPilotModel.teacherId];
        if (flag) {
            NSLog(@"插入成功");
        } else {
            NSLog(@"插入失败");
        }
        
    }];
}

- (NSMutableArray *)selectAllStudyPilotModel
{
    NSMutableArray *array = [NSMutableArray array];
    
    [self.queue inDatabase:^(FMDatabase *db) {
        
        FMResultSet *result = [db executeQuery:@"select * from t_Collection;"];
        
        while ([result next]) {
        
            NSString *position = [result stringForColumn:@"position"];
            NSString *iconUrl = [result stringForColumn:@"iconUrl"];
            NSString *level = [result stringForColumn:@"level"];
            NSString *likeNo = [result stringForColumn:@"likeNo"];
            NSString *name = [result stringForColumn:@"name"];
            NSString *serviceContent = [result stringForColumn:@"serviceContent"];
            NSString *serviceTitle = [result stringForColumn:@"serviceTitle"];
            NSString *teacherId = [result stringForColumn:@"teacherId"];

            StudyPilotModel *studyPilotM = [[StudyPilotModel alloc] init];
            
            studyPilotM.name = name;
            studyPilotM.position = position;
            studyPilotM.iconUrl = iconUrl;
            studyPilotM.level = level;
            studyPilotM.likeNo = likeNo;
            studyPilotM.serviceContent = serviceContent;
            studyPilotM.serviceTitle = serviceTitle;
            studyPilotM.teacherId = teacherId;
            
            [array addObject:studyPilotM];
        }
    }];
    return array;
}

- (void)deleteAllStudyPilot
{
    [self.queue inDatabase:^(FMDatabase *db) {
        
        [db executeUpdate:@"delete from t_Collection;"];
        
    }];
}

- (StudyPilotModel *)selectWithteacherId:(NSString *)teacherId;
{
    StudyPilotModel *studyPliotM = [[StudyPilotModel alloc] init];
    
    [self.queue inDatabase:^(FMDatabase *db) {
        
        FMResultSet *result = [db executeQuery:@"select * from t_Collection where teacherId = ?;", teacherId];
        
        while ([result next]) {
            
            NSString *position = [result stringForColumn:@"position"];
            NSString *iconUrl = [result stringForColumn:@"iconUrl"];
            NSString *level = [result stringForColumn:@"level"];
            NSString *likeNo = [result stringForColumn:@"likeNo"];
            NSString *name = [result stringForColumn:@"name"];
            NSString *serviceContent = [result stringForColumn:@"serviceContent"];
            NSString *serviceTitle = [result stringForColumn:@"serviceTitle"];
            NSString *teacherId = [result stringForColumn:@"teacherId"];

            StudyPilotModel *studyPilotM = [[StudyPilotModel alloc] init];
            
            studyPilotM.name = name;
            studyPilotM.position = position;
            studyPilotM.iconUrl = iconUrl;
            studyPilotM.level = level;
            studyPilotM.likeNo = likeNo;
            studyPilotM.serviceContent = serviceContent;
            studyPilotM.serviceContent = serviceTitle;
            studyPilotM.teacherId = teacherId;

            
        }
        
    }];
    
    if (studyPliotM.teacherId == nil) {
        return nil;
    }
    
    return studyPliotM;
}

- (void)deleteFromDBWithTeacherId:(NSString *)teacherId
{
    [self.queue inDatabase:^(FMDatabase *db) {
        
        [db executeUpdate:@"delete from t_Collection where teacherId = ?", teacherId];
        
    }];
}


@end
