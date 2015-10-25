//
//  StudyPilotViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "StudyPilotViewController.h"
#import "PilotTableViewCell.h"
#import "AFNConnect.h"
#import "JSONKit.h"
#import "StudyPilotModel.h"
#import "tutorHomeViewController.h"

@interface StudyPilotViewController ()

@property (nonatomic, strong) NSMutableArray *studyPilotArray;
@property (nonatomic, copy) NSString *pilotUpdataID;
@property (nonatomic, copy) NSString *pilotFirstID;
@property (nonatomic, copy) NSString *pilotLastID;
@property (nonatomic, assign) BOOL downUpdata;

@end

@implementation StudyPilotViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.studyPilotArray = [NSMutableArray array];
        self.pilotFirstID = @"min";
        //self.pilotLastID = @"max";
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1];
    //self.view.backgroundColor = [UIColor yellowColor];
    
    [self setSubviews];
    
    self.downUpdata = YES;
    [self getListData:self.pilotFirstID withisFirst:YES];
}

-(void)setSubviews
{
    UIButton *pushBT = [UIButton buttonWithType:UIButtonTypeCustom];
    pushBT.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    //[pushBT setBackgroundColor:[UIColor blackColor]];
    [pushBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [pushBT addTarget:self action:@selector(popToRoot:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:pushBT];
    
    UILabel *pilotTitleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 40, pushBT.frame.origin.y, 80, pushBT.frame.size.height)];
    pilotTitleL.text = @"留学领航";
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    self.pilotTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.pilotTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.pilotTV.showsVerticalScrollIndicator = NO;
    self.pilotTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.pilotTV];

    self.pilotTV.dataSource = self;
    self.pilotTV.delegate = self;
}

-(void)popToRoot:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewDidDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_studyPilotArray.count == 0) {
        
        return 0;
    }
    return _studyPilotArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *commonCellIdentifier = @"pilotCell";
    
    PilotTableViewCell *pilotCommonCell = [tableView dequeueReusableCellWithIdentifier:commonCellIdentifier];
    
    if (pilotCommonCell == nil) {
        pilotCommonCell = [[PilotTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:commonCellIdentifier];
        pilotCommonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        pilotCommonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
        //pilotCommonCell.backgroundColor = [UIColor yellowColor];
    }
    
    if (_studyPilotArray.count == 0) {
        
        return pilotCommonCell;
    }
    
    StudyPilotModel *tmp = [_studyPilotArray objectAtIndex:indexPath.row];
    [pilotCommonCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
//    pilotCommonCell.schoolL.text = tmp.schoolName;
//    CGRect schoolRect = [tmp.schoolName boundingRectWithSize:CGSizeMake(200, MAXFLOAT) options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:pilotCommonCell.schoolL.font} context:nil];
//    schoolRect.size.height = pilotCommonCell.schoolL.frame.size.height;
//    pilotCommonCell.schoolL.frame = CGRectMake(pilotCommonCell.schoolL.frame.origin.x, pilotCommonCell.schoolL.frame.origin.y, schoolRect.size.width, schoolRect.size.height);
//    
//    pilotCommonCell.positionL.text = tmp.major;
//    CGRect pilotCommonRect = [pilotCommonCell.positionL.text boundingRectWithSize:CGSizeMake(200, MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingUsesFontLeading attributes:@{NSFontAttributeName:pilotCommonCell.positionL.font} context:nil];
//    pilotCommonRect.size.height = pilotCommonCell.positionL.frame.size.height;
//    pilotCommonCell.positionL.frame = CGRectMake(pilotCommonCell.positionL.frame.origin.x, pilotCommonCell.positionL.frame.origin.y, pilotCommonRect.size.width, pilotCommonRect.size.height);
    
    pilotCommonCell.simInfoL.text = [NSString stringWithFormat:@"%@ %@", tmp.schoolName, tmp.major];
    
    pilotCommonCell.tutorNameL.text = tmp.name;
    pilotCommonCell.topicL.text = tmp.serviceTitle;
    pilotCommonCell.hasSeenL.text = [NSString stringWithFormat:@"%@人想见", tmp.likeNo];
    pilotCommonCell.clickNumberL.text = [NSString stringWithFormat:@"本周可咨询%@次", tmp.timeperweek];
    
    return pilotCommonCell;
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_studyPilotArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    StudyPilotModel *tmp = [_studyPilotArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.serviceTitleStr = tmp.serviceTitle;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark --请求数据
-(void)getListData:(NSString *)userId withisFirst:(BOOL)isFirst
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForTeacherList:userId withdownUpdata:_downUpdata withisFirst:isFirst] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
//        NSLog(@"==========result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        
        NSMutableArray *array = [NSMutableArray arrayWithArray:jsonArray];
        
        if (array.count != 0) {
            
            for (NSMutableDictionary *stuDic in array) {
                
                StudyPilotModel *pilotModel = [[StudyPilotModel alloc] init];
                
                pilotModel.iconUrl = [stuDic objectForKey:@"iconUrl"];
                pilotModel.level = [stuDic objectForKey:@"level"];
                pilotModel.likeNo = [stuDic objectForKey:@"likeNo"];
                pilotModel.name = [stuDic objectForKey:@"name"];
                pilotModel.simpleShow1 = [stuDic objectForKey:@"simpleShow1"];
                pilotModel.simpleShow2 = [stuDic objectForKey:@"simpleShow2"];
                pilotModel.serviceContent = [stuDic objectForKey:@"serviceContent"];
                pilotModel.serviceTitle = [stuDic objectForKey:@"serviceTitle"];
                pilotModel.teacherId = [[stuDic objectForKey:@"teacherId"] description];
                pilotModel.companyName = [stuDic objectForKey:@"companyName"];
                pilotModel.position = [stuDic objectForKey:@"position"];
                pilotModel.schoolName = [stuDic objectForKey:@"schoolName"];
                pilotModel.major = [stuDic objectForKey:@"major"];
                pilotModel.timeperweek = [stuDic objectForKey:@"timeperweek"];
                
                [self.studyPilotArray addObject:pilotModel];
            }
        }
        
        //NSLog(@"留学领航 %@", _studyPilotArray);
        [self.pilotTV reloadData];
    }];

}

-(void)showAlert:(NSString *)message
{
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:0.8f target:self selector:@selector(timerFireMethod:) userInfo:promptAlert repeats:YES];
    
    [promptAlert show];
}

-(void)timerFireMethod:(NSTimer *)theTimer
{
    UIAlertView *promptAlert = (UIAlertView *)[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
