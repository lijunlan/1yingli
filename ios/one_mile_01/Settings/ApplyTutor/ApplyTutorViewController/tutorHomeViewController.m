//
//  tutorHomeViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorHomeViewController.h"

@interface tutorHomeViewController ()

@end

@implementation tutorHomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    self.scrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + NAVIGATIONHEIGHT,self.view.frame.size.width ,self.view.frame.size.height)];
    self.scrollView.backgroundColor = [UIColor whiteColor];
    self.scrollView.contentSize = CGSizeMake(0,667 * 3);
    self.scrollView.bounces = NO;
    //self.scrollView.pagingEnabled = YES;
    [self.view addSubview:self.scrollView];
    
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 30, 30, 30);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backButton) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    //    收藏
    UIButton *button1 = [UIButton buttonWithType:UIButtonTypeCustom];
    button1.frame = CGRectMake(button.frame.origin.x + 240, button.frame.origin.y + 5, button.frame.size.width, button.frame.size.height);
    [button1 setBackgroundImage:[UIImage imageNamed:@"tutorHome_favor.png"] forState:UIControlStateNormal];
    button1.backgroundColor = [UIColor clearColor];
    [self.view addSubview:button1];
    
//    分享
    UIButton *button2 = [UIButton buttonWithType:UIButtonTypeCustom];
    button2.frame = CGRectMake(button1.frame.origin.x + 50, button1.frame.origin.y, button1.frame.size.width, 25);
    [button2 setBackgroundImage:[UIImage imageNamed:@"translation.png"] forState:UIControlStateNormal];
    button2.backgroundColor = [UIColor clearColor];
    [self.view addSubview:button2];
    
//    头像
    self.imageView = [[UIImageView alloc]initWithFrame:CGRectMake(20, 5, 100, 100)];
    self.imageView.backgroundColor = [UIColor redColor];
    //设置圆角
    self.imageView.layer.masksToBounds = YES;
    self.imageView.layer.cornerRadius = 50;
    //边框宽度
    self.imageView.layer.borderWidth = 0;
    [self.scrollView addSubview:self.imageView];
    
//    姓名
    self.nameLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView.frame.origin.x + 120, self.imageView.frame.origin.y + 10 , 100, 30)];
    self.nameLabel.text = @"杜潇潇";
    self.nameLabel.font = [UIFont systemFontOfSize:22];
    self.nameLabel.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:self.nameLabel];
    
//    介绍
    self.label2 = [[UILabel alloc]initWithFrame:CGRectMake(self.nameLabel.frame.origin.x, self.nameLabel.frame.origin.y + self.nameLabel.frame.size.height +  10 , 200, 30)];
    self.label2.text = @"    新媒体文化传播创始人";
    self.label2.textColor = [UIColor colorWithRed:67 / 255.0 green:172 / 255.0 blue:226 /255.0 alpha:1.0];
    self.label2.layer.borderWidth = 0.5;
    self.label2.layer.borderColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1].CGColor;
    self.label2.layer.cornerRadius = 15;
    [self.scrollView addSubview:self.label2];
 
//    两个横线
    UILabel *label3 = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.origin.x + 10, self.imageView.frame.origin.y + self.imageView.frame.size.height + 10, self.view.frame.size.width - 20, 0.5)];
    label3.backgroundColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    [self.scrollView addSubview:label3];
    
    UILabel *label4 = [[UILabel alloc]initWithFrame:CGRectMake(label3.frame.origin.x,label3.frame.origin.y + 27,label3.frame.size.width, label3.frame.size.height)];
    label4.backgroundColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    [self.scrollView addSubview:label4];
    
    
//  四个小图片
//    茶杯
    UIImageView *imageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(label3.frame.origin.x + 3, label3.frame.origin.y + 6, 20, 20)];
    imageView1.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    imageView1.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView1];
//    7
    UILabel *label5 = [[UILabel alloc]initWithFrame: CGRectMake(imageView1.frame.origin.x + imageView1.frame.size.width + 2, imageView1.frame.origin.y + 7, 8, 10)];
    label5.text = @"7";
    label5.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label5.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label5];
//    人见过
    
    UILabel *label6 = [[UILabel alloc]initWithFrame: CGRectMake(label5.frame.origin.x + label5.frame.size.width, label5.frame.origin.y, 40, 10)];
    label6.text = @"人见过";
    label6.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label6.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label6];
    
    
//常驻地址图片
    UIImageView *imageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(label6.frame.origin.x +label6.frame.size.width + 5, imageView1.frame.origin.y, imageView1.frame.size.width, imageView1.frame.size.height)];
    imageView2.image = [UIImage imageNamed:@"tutorHome_position.png"];
    imageView2.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView2];
    //    常驻纽约
    UILabel *label7 = [[UILabel alloc]initWithFrame: CGRectMake(imageView2.frame.origin.x + imageView2.frame.size.width + 3, imageView1.frame.origin.y + 7, 50, 10)];
    label7.text = @"常驻纽约";
    label7.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label7.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label7];

//    想约心形图片
    UIImageView *imageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(label7.frame.origin.x +label7.frame.size.width + 10, imageView2.frame.origin.y , imageView2.frame.size.width, imageView2.frame.size.height)];
    imageView3.image = [UIImage imageNamed:@"tutorHome_favor.png"];
    imageView3.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView3];
    //    15
    UILabel *label8 = [[UILabel alloc]initWithFrame: CGRectMake(imageView3.frame.origin.x + imageView3.frame.size.width + 3, imageView3.frame.origin.y + 7, 15, 10)];
    label8.text = @"15";
    label8.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label8.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label8];
    
//    人想见
    UILabel *label9 = [[UILabel alloc]initWithFrame: CGRectMake(label8.frame.origin.x + label8.frame.size.width, label8.frame.origin.y, 40, 10)];
    label9.text = @"人想见";
    label9.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label9.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label9];
    
    
    
    //    本周可预约图片
    UIImageView *imageView4 = [[UIImageView alloc]initWithFrame:CGRectMake(label9.frame.origin.x +label9.frame.size.width + 5, imageView3.frame.origin.y , imageView3.frame.size.width, imageView3.frame.size.height)];
    imageView4.image = [UIImage imageNamed:@"tutorHome_times.png"];
    imageView4.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView4];
    //    本周可预约
    UILabel *label10 = [[UILabel alloc]initWithFrame: CGRectMake(imageView4.frame.origin.x + imageView4.frame.size.width + 3, imageView4.frame.origin.y + 7, 60, 10)];
    label10.text = @"本周可预约";
    label10.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label10.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label10];
    
    //    5
    UILabel *label11 = [[UILabel alloc]initWithFrame: CGRectMake(label10.frame.origin.x + label10.frame.size.width + 2, label10.frame.origin.y, 8, 10)];
    label11.text = @"5";
    label11.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label11.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label11];
//    次
    UILabel *label12 = [[UILabel alloc]initWithFrame: CGRectMake(label11.frame.origin.x + label11.frame.size.width , label11.frame.origin.y, 12, 10)];
    label12.text = @"次";
    label12.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label12.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label12];

    
//    蓝色部分教你如何利用互联网创业
    UIImageView *imageView5 = [[UIImageView alloc]initWithFrame:CGRectMake(label3.frame.origin.x, label12.frame.origin.y + label12.frame.size.height + 20,self.view.frame.size.width - label3.frame.origin.x * 2, 230)];
    imageView5.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    imageView5.layer.cornerRadius = 10;
    [self.scrollView addSubview:imageView5];
//    200元/次
    UILabel *label13 = [[UILabel alloc]initWithFrame:CGRectMake(imageView5.frame.origin.x + 15,imageView5.frame.origin.y + 15,100,25)];
    label13.layer.masksToBounds = YES;
    label13.layer.cornerRadius = 12;
    label13.text = @"  200 元/次";
    label13.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    label13.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:label13];
    
//    文章标题

    UILabel *label14 = [[UILabel alloc]initWithFrame:CGRectMake(label13.frame.origin.x + 30,label13.frame.origin.y + label13.frame.size.height + 10,300,25)];
    label14.text = @"教你如何利用互联网就业";
    label14.textColor = [UIColor whiteColor];
    label14.font = [UIFont systemFontOfSize:22 weight:8];
    [self.scrollView addSubview:label14];
    
//    文章内容
    
    UILabel *label15 = [[UILabel alloc]initWithFrame:CGRectMake(label14.frame.origin.x,label14.frame.origin.y + label14.frame.size.height + 10,300,25)];
    label15.text = @"教你如何利用互联网就业";
    label15.textColor = [UIColor whiteColor];
    
    label15.numberOfLines = 4;
    label15.font = [UIFont systemFontOfSize:18 weight:5];
    [self.scrollView addSubview:label15];
    
    //    展示更多按钮
    UIButton *button5 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 80, self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20, 160, 35)];
    button5.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    [button5 setBackgroundImage:[UIImage imageNamed:@""] forState:UIControlStateNormal];
    button5.layer.cornerRadius = 5;
    [button5 setTitle:@"查看更多评价" forState:UIControlStateNormal];
    [button5 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.scrollView addSubview:button5];
    
    

//    关于ta
    UILabel *label16 = [[UILabel alloc]initWithFrame:CGRectMake(imageView5.frame.origin.x,imageView5.frame.origin.y + imageView5.frame.size.height + 10,100,25)];
    label16.text = @"关于 ta";
    label16.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    label16.numberOfLines = 4;
    label16.font = [UIFont systemFontOfSize:22];
    [self.scrollView addSubview:label16];
    

//照片
    
    UIImageView *imageView6 = [[UIImageView alloc]initWithFrame:CGRectMake(imageView5.frame.origin.x, label16.frame.origin.y + label16.frame.size.height + 10,self.view.frame.size.width - label16.frame.origin.x * 2, 230)];
    imageView6.backgroundColor = [UIColor redColor];
    [self.scrollView addSubview:imageView6];
    
//    导师评价
    UILabel *label17 = [[UILabel alloc]initWithFrame:CGRectMake(imageView6.frame.origin.x + 3 ,imageView6.frame.origin.y + imageView6.frame.size.height + 10,self.view.frame.size.width - imageView6.frame.origin.x * 2 - 6,300)];
    label17.text = @"待定";
    label17.backgroundColor = [UIColor yellowColor];
    label17.numberOfLines = 4;
    label17.font = [UIFont systemFontOfSize:16];
    [self.scrollView addSubview:label17];
    
    
    //    学员评价
    UILabel *label18 = [[UILabel alloc]initWithFrame:CGRectMake(imageView6.frame.origin.x,label17.frame.origin.y + label17.frame.size.height + 10,100,25)];
    label18.text = @"学员评价";
    label18.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    label18.font = [UIFont systemFontOfSize:22];
    [self.scrollView addSubview:label18];

//    学员评价tableView
    self.tableView1 = [[UITableView alloc]initWithFrame:CGRectMake(label18.frame.origin.x, label18.frame.origin.y + label18.frame.size.height + 10, imageView6.frame.size.width, 450) style:UITableViewStylePlain];
    self.tableView1.backgroundColor = [UIColor whiteColor];
    self.tableView1.delegate = self;
    self.tableView1.dataSource = self;
    self.tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.scrollView addSubview:self.tableView1];
  
//    查看更多评价按钮
    UIButton *button3 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 80, self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20, 160, 35)];
    button3.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    button3.layer.cornerRadius = 5;
    [button3 setTitle:@"查看更多评价" forState:UIControlStateNormal];
    [button3 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.scrollView addSubview:button3];
    
    
    //    学员评价
    UILabel *label19 = [[UILabel alloc]initWithFrame:CGRectMake(imageView6.frame.origin.x,button3.frame.origin.y + button3.frame.size.height + 20,200,25)];
    label19.text = @"相关话题";
    label19.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    label19.font = [UIFont systemFontOfSize:22];
    [self.scrollView addSubview:label19];
    
    
    //    学员评价tableView
    self.tableView2 = [[UITableView alloc]initWithFrame:CGRectMake(label19.frame.origin.x, label19.frame.origin.y + label19.frame.size.height + 10, imageView6.frame.size.width, 300) style:UITableViewStylePlain];
    self.tableView2.tag = 10010;
    self.tableView2.backgroundColor = [UIColor whiteColor];
    self.tableView2.delegate = self;
    self.tableView2.dataSource = self;
    self.tableView2.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.scrollView addSubview:self.tableView2];

    
    //    立即约见
    UIButton *button4 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.origin.x,self.view.frame.size.height - 60,self.view.frame.size.width,60)];
    button4.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    [button4 setTitle:@"立即约见" forState:UIControlStateNormal];
    [button4 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.view addSubview:button4];
    
    
    
    
    
    
    // Do any additional setup after loading the view.
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (tableView.tag == 10010) {
        return 2;
    }
    return 3;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 150;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier2 = @"myCell2";
    talkTableViewCell *cell2 = [tableView dequeueReusableHeaderFooterViewWithIdentifier:cellIdentifier2];
    
    static NSString *cellIdentifier = @"myCell";
    appraiseTabelViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if (self.tableView2 == tableView) {
        if (cell2 == nil) {
            cell2 = [[talkTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier2];
            cell2.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        }
        
    }else{
        if (cell == nil) {
            cell = [[appraiseTabelViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        
        }
    }
    
    if (tableView == _tableView2) {
        
        return cell2;
    } else {
    
        return cell;
    }

}


-(void)backButton{
[self dismissViewControllerAnimated:YES completion:^{
    
    
}];
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
