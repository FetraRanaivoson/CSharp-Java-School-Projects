   M S B A M L   `   `   `  ����F  BBindingDemo, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null)clr-namespace:BindingDemoBindingDemo  5   	       BindingDemo.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   +localclr-namespace:BindingDemo     5   	     � Title$  Binding demo��5   	   $	��500��5	   	   $	��150��6    � 	Resources 5
       �� 
TargetType �� Property �� Value��%�   '��       �� 5   
   " ��6   .�5      ��  � ��$ 10��6&   �� 5       � ��$ 10��6'   �� 5       �@J�Opacity$ 0Q�6(   5      5      .��5      ��  �� Orientation$ Vertical=�6   $��Center=�5      $��20q�5      .+�5   
   ]� .+�5      �� $
��Name��6   $
��Left=�6#   �� 5      $
��Left=�6   �� 5      $��Right=�5      ��5      ��  �� Path$ SubViewModel.Name� �� Mode$ TwoWay=�5      5      �� 5   
   �� 5    
   $ 
Horizontal=�6   .+�5!      �� -   5        $��Set Name = AAAAA��5!      $
��Left=�5"      5#      �� 5$      �� 5%      -   5        $��Set Name = 12345��5%      $
��Left=�5&      5'      �� 5(      �� 5)      -   5        $��Set Name Empty��5)      $
��Left=�5*      5+      �� 5,      �� 5-      -   5        $��
BREAKPOINT��5-      $
��Left=�5.      5/      50      52      53      