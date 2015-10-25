//
//  EntrepreneurialViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EntrepreneurialViewController.h"
#import "EntrepreneurialTableViewCell.h"
#import "PowerModel.h"
#import "tutorHomeViewController.h"

@interface EntrepreneurialViewController ()

@property (nonatomic, strong) NSMutableArray *entreArray;
@property (nonatomic, assign) NSString *entreFirstID;
@end

@implementation EntrepreneurialViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.entreArray = [NSMutableArray array];
        self.entreFirstID = @"min";
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    [self setSubviews];
    
    [self getListData:self.entreFirstID withisFirst:YES];
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
    pilotTitleL.text = @"创业助力";
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    self.entreTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.entreTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.entreTV.showsVerticalScrollIndicator = NO;
    self.entreTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.entreTV];
    
    self.entreTV.dataSource = self;
    self.entreTV.delegate = self;
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
    if (_entreArray.count == 0) {
        
        return 0;
    }
    
    return _entreArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *entreCellIdentifier = @"entreCell";
    
    EntrepreneurialTableViewCell *entreCommonCell = [tableView dequeueReusableCellWithIdentifier:entreCellIdentifier];
    
    if (entreCommonCell == nil) {
        entreCommonCell = [[EntrepreneurialTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:entreCellIdentifier];
        entreCommonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        entreCommonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
        //pilotCommonCell.backgroundColor = [UIColor yellowColor];
    }
    
    if (_entreArray.count == 0) {
        
        return entreCommonCell;
    }
    
    PowerModel *tmp = [_entreArray objectAtIndex:indexPath.row];
    [entreCommonCell.entreTutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    entreCommonCell.entreTutorNameL.text = tmp.name;
    entreCommonCell.topicForEntreL.text = tmp.serviceTitle;
    
//    entreCommonCell.schoolForEntreL.text = tmp.companyName;
//    
//    entreCommonCell.positionForEntreL.text = tmp.position;
    entreCommonCell.simInfoForEntreL.text = [NSString stringWithFormat:@"%@ %@", tmp.companyName, tmp.position];
    
    entreCommonCell.hasSeenForEntreL.text = [NSString stringWithFormat:@"%@人想过", tmp.likeNo];
    entreCommonCell.clickNumberForEntreL.text = [NSString stringWithFormat:@"本周可咨询%@次", tmp.timeperweek];
    
    return entreCommonCell;
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_entreArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    PowerModel *tmp = [_entreArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.serviceTitleStr = tmp.serviceTitle;
    tutorVC.powerM = tmp;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark --请求数据
-(void)getListData:(NSString *)userId withisFirst:(BOOL)isFirst
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForList:@"4" withNextID:userId withdownUpdata:YES withisFirst:isFirst] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"==========result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        
        for (NSDictionary *dic in jsonArray) {
            
            PowerModel *powerM = [[PowerModel alloc] init];
            
            powerM.teacherId = [dic objectForKey:@"teacherId"];
            powerM.iconUrl = [dic objectForKey:@"iconUrl"];
            powerM.level = [dic objectForKey:@"level"];
            powerM.likeNo = [dic objectForKey:@"likeNo"];
            powerM.name = [dic objectForKey:@"name"];
            powerM.serviceTitle = [dic objectForKey:@"serviceTitle"];
            powerM.serviceContent = [dic objectForKey:@"serviceContent"];
            powerM.simpleShow1 = [dic objectForKey:@"simpleShow1"];
            powerM.simpleShow2 = [dic objectForKey:@"simpleShow2"];
            powerM.companyName = [dic objectForKey:@"companyName"];
            powerM.position = [dic objectForKey:@"position"];
            powerM.schoolName = [dic objectForKey:@"schoolName"];
            powerM.major = [dic objectForKey:@"major"];
            powerM.timeperweek = [dic objectForKey:@"timeperweek"];
            
            [self.entreArray addObject:powerM];
        }
        
        [self.entreTV reloadData];
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
