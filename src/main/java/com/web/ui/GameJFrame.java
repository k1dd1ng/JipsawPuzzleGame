package com.web.ui;

import sun.awt.ModalityListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author cjw
 */

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    //关键:继承JFrame
    //游戏主界面

    //创建一个二维数组
    //目的：用来管理数据，根据图片的数据进行加载
    int[][] data = new int[4][4];

    //空白图片的位置(0)
    int x = 0;
    int y = 0;

    //胜利数组
    int[][] win = new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //图片路径
    String path = "image/animal/animal3/";

    //图片大类
    String[] mold = {"/animal", "/girl", "/sport"};

    //计数器
    int step = 0;

    public GameJFrame() {
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据(打乱)
        initData();

        //初始化图片（打乱之后加载图片）
        initImage();

        //显示界面
        this.setVisible(true);
    }

    //初始化数据(打乱)
    private void initData() {

        //1.初始化数组
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //2.打乱数组中的数据
        //遍历数组，得到每一个元素，拿着每一个元素跟随机素引上的数据进行交换
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        //3.实现 一维数组到二维数组的传输
        //3.1.方法一 以二维数组为基础
        /*for(int i = 0; i < data.length; i++){
            for(int j=0;j<data[i].length;j++){
                data[i][j] = arr[i*4+j];
            }
        }*/
        //3.2.方法二 以一维数组为基础
        for (int i = 0; i < arr.length; i++) {
            //获取0的位置
            if (arr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = arr[i];
        }
    }

    //初始化图片
    //根据二维数组data中的数据来初始化
    private void initImage() {

        //先加载的图片在上方
        //所以要清空图片,再重新加载
        this.getContentPane().removeAll();

        //判断胜利
        if (victory()) {
            JLabel winLabel = new JLabel(new ImageIcon("image/win.png"));
            winLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winLabel);
        }

        //计数器
        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(475, 30, 100, 20);
        // 设置字体和大小
        // 字体名称, 样式, 大小
        Font font = new Font("微软雅黑", Font.BOLD, 16);
        stepCount.setFont(font);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                //获取图片
                int count = data[i][j];

                //创建一个图片ImageIcon的对象
                ImageIcon icon = new ImageIcon(path + count + ".jpg");

                //创建一个JLabel的对象(管理容器)
                JLabel jLabel = new JLabel(icon);

                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);

                //给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));

                //把管理容器添加到界面中(默认中央)
                this.getContentPane().add(jLabel);
            }
        }

        //添加背景图片
        ImageIcon bg = new ImageIcon("image/background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(40, 40, bg.getIconWidth(), bg.getIconHeight());
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
    }

    //初始化菜单
    private void initJMenuBar() {

        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面的选项对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu replaceMenu = new JMenu("更换图片");

        //创建选项下面的条目对象
        JMenuItem replayItem = new JMenuItem("重新开始");
        JMenuItem reLoginItem = new JMenuItem("重新登陆");
        JMenuItem closeItem = new JMenuItem("关闭游戏");

        JMenuItem feedBackItem = new JMenuItem("意见反馈");
        JMenuItem versionItem = new JMenuItem("版本信息");
        JMenuItem shortcutItem = new JMenuItem("快捷方式");

        JMenuItem animalItem = new JMenuItem("动物");
        JMenuItem girlItem = new JMenuItem("美女");
        JMenuItem sportItem = new JMenuItem("运动");


        //将每一个选项下面的条目添加到对应选项中
        functionJMenu.add(replaceMenu);
        functionJMenu.add(replayItem);
        //functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(feedBackItem);
        aboutJMenu.add(versionItem);
        aboutJMenu.add(shortcutItem);

        replaceMenu.add(animalItem);
        replaceMenu.add(girlItem);
        replaceMenu.add(sportItem);

        //给条目绑定事件
        replayItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replay();
            }
        });
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //直接关闭虚拟机即可
                System.exit(0);
            }
        });

        feedBackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个弹窗对象
                JDialog jDialog = new JDialog();
                // 使用 BorderLayout 布局
                jDialog.setLayout(new BorderLayout());

                // 创建文字标签
                String feedbackText = "<html><div style='text-align: center; font-family: 微软雅黑;'>"
                        + "<p>您的建议对我们非常重要！</p>"
                        + "<p>如有任何意见或者问题</p>"
                        + "<p>欢迎联系下方微信(备注'游戏反馈')</p>"
                        + "<h3>万分感谢！！！</h3>"
                        + "</div></html>";
                JLabel textLabel = new JLabel(feedbackText);
                textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                textLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

                // 创建图片标签（下方贴底）
                ImageIcon icon = new ImageIcon("image/feedback.jpg");
                JLabel imageLabel = new JLabel(new ImageIcon(scaleImage(icon.getImage(), 330, 455)));
                // 图片贴底对齐
                imageLabel.setVerticalAlignment(JLabel.BOTTOM);

                // 创建容器面板
                JPanel contentPanel = new JPanel(new BorderLayout());
                // 文字在上方
                contentPanel.add(textLabel, BorderLayout.NORTH);
                // 图片占据剩余空间
                contentPanel.add(imageLabel, BorderLayout.CENTER);

                // 设置面板大小（宽度330，高度555）
                contentPanel.setPreferredSize(new Dimension(330, 555));

                // 把面板添加到弹窗
                jDialog.getContentPane().add(contentPanel);

                // 弹窗设置
                jDialog.setTitle("意见反馈");
                // 自动调整窗口大小
                jDialog.pack();
                jDialog.setAlwaysOnTop(true);
                jDialog.setLocationRelativeTo(null);
                jDialog.setModal(true);
                jDialog.setVisible(true);
            }
        });
        versionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建版本信息弹窗
                JDialog versionDialog = new JDialog();
                versionDialog.setTitle("版本信息");
                versionDialog.setLayout(new BorderLayout());
                versionDialog.setResizable(false);

                // 使用HTML格式化文本（居中+紧凑排版）
                String versionInfo = "<html><div style='"
                        + "text-align: center; "
                        + "font-family: 微软雅黑; "
                        + "line-height: 1.3; "
                        + "margin: 0; "
                        + "padding: 15px 20px;"
                        + "'>"
                        + "<h3 style='margin: 0 0 10px 0;'>拼图游戏单机版</h3>"
                        + "<p style='margin: 5px 0;'>版本号：v1.0</p>"
                        + "<p style='margin: 5px 0;'>开发团队：M0nster</p>"
                        + "<p style='margin: 5px 0;'>发布日期：2025-07-02</p>"
                        + "<p style='margin: 5px 0;'>版权所有 @ALL</p>"
                        + "</div></html>";

                JLabel infoLabel = new JLabel(versionInfo);
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

                // 组装弹窗内容
                JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
                contentPanel.add(infoLabel, BorderLayout.CENTER);
                // 弹窗设置
                versionDialog.add(contentPanel);
                versionDialog.pack();
                versionDialog.setAlwaysOnTop(true);
                versionDialog.setLocationRelativeTo(null);
                versionDialog.setModal(true);
                versionDialog.setVisible(true);
            }
        });
        shortcutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建版本信息弹窗
                JDialog versionDialog = new JDialog();
                versionDialog.setTitle("快捷方式");
                versionDialog.setLayout(new BorderLayout());
                versionDialog.setResizable(false);

                // 使用HTML格式化文本（居中+紧凑排版）
                String versionInfo = "<html><div style='"
                        + "text-align: center; "
                        + "font-family: 微软雅黑; "
                        + "line-height: 1.3; "
                        + "margin: 0; "
                        + "padding: 15px 20px;"
                        + "'>"
                        + "<h3 style='margin: 0 0 10px 0;'>快捷键</h3>"
                        + "<p style='margin: 5px 0;'>直接胜利：数字0</p>"
                        + "<p style='margin: 5px 0;'>查看最终结果：数字1(长按)</p>"
                        + "<p style='margin: 5px 0;'>重新开始：数字2</p>"
                        + "<p style='margin: 5px 0;'>关闭游戏：Esc</p>"
                        + "<p style='margin: 5px 0;'>更换图片：动物(6) 美女(7) 运动(8)</p>"
                        + "</div></html>";

                JLabel infoLabel = new JLabel(versionInfo);
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

                // 组装弹窗内容
                JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
                contentPanel.add(infoLabel, BorderLayout.CENTER);
                // 弹窗设置
                versionDialog.add(contentPanel);
                versionDialog.pack();
                versionDialog.setAlwaysOnTop(true);
                versionDialog.setLocationRelativeTo(null);
                versionDialog.setModal(true);
                versionDialog.setVisible(true);
            }
        });

        animalItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replaceImage(0,8);
            }
        });
        girlItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replaceImage(1,11);
            }
        });
        sportItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replaceImage(2,10);
            }
        });

        //将菜单里面的两个选项添加到菜单之中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    //初始化界面
    private void initJFrame() {
        //设置界面宽高
        this.setSize(603, 680);
        //设置界面标题
        this.setTitle("拼图游戏单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中(相对屏幕)
        this.setLocationRelativeTo(null);
        //设置游戏关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);

        //给整个界面添加键盘监听事件
        this.addKeyListener(this);


    }

    //判断胜利
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //按比例缩放图片
    public Image scaleImage(Image src, int targetWidth, int targetHeight) {
        // 按比例计算目标尺寸（保持宽高比）
        int originalWidth = src.getWidth(null);
        int originalHeight = src.getHeight(null);

        // 计算缩放比例
        double widthRatio = (double) targetWidth / originalWidth;
        double heightRatio = (double) targetHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        // 按比例缩放
        int scaledWidth = (int) (originalWidth * ratio);
        int scaledHeight = (int) (originalHeight * ratio);

        return src.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
    }

    //重新开始游戏
    public void replay() {
        step = 0;
        initData();
        initImage();
    }

    //更换图片
    private void replaceImage(int x,int y) {
        Random r = new Random();
        String replacePath = path;
        do {
            path = "image" + mold[x] + mold[x] + (r.nextInt(y) + 1) + "/";
        }while(replacePath.equals(path));
        replay();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 49) {
            //把界面中所有图片删除
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //添加背景图片
            ImageIcon bg = new ImageIcon("image/background.png");
            JLabel background = new JLabel(bg);
            background.setBounds(40, 40, bg.getIconWidth(), bg.getIconHeight());
            this.getContentPane().add(background);

            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(code);
        //判斯游戏是否胜利，如果胜利，则不能再执行下面的移动代码了
        if (!victory()) {
            //对上下左右进行判断
            //左:37 上:38 右:39 :下:40
            if (code == 37) {
                if (y != 3) {
                    System.out.println("向左移动");
                    data[x][y] = data[x][y + 1];
                    data[x][++y] = 0;
                    step++;
                    initImage();
                }
            } else if (code == 38) {
                if (x != 3) {
                    System.out.println("向上移动");
                    data[x][y] = data[x + 1][y];
                    data[++x][y] = 0;
                    step++;
                    initImage();
                }
            } else if (code == 39) {
                if (y != 0) {
                    System.out.println("向右移动");
                    data[x][y] = data[x][y - 1];
                    data[x][--y] = 0;
                    step++;
                    initImage();
                }
            } else if (code == 40) {
                if (x != 0) {
                    System.out.println("向下移动");
                    data[x][y] = data[x - 1][y];
                    data[--x][y] = 0;
                    step++;
                    initImage();
                }
            }
        }
        if (code == 48) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    data[i][j] = i * 4 + j + 1;
                }
            }
            x = 3;
            y = 3;
            data[x][y] = 0;
            initImage();
        } else if (code == 49) {
            initImage();
        } else if (code == 50) {
            replay();
        } else if (code == 27) {
            //Esc
            //直接关闭虚拟机即可
            System.exit(0);
        }else if (code==54){
            replaceImage(0,8);
        }else if (code == 55) {
            replaceImage(1,11);
        }else if (code == 56) {
            replaceImage(2,10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
