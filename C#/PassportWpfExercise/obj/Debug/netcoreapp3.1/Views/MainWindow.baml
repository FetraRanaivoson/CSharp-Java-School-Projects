   M S B A M L   `   `   `  ����N  JPassportWpfExercise, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null5clr-namespace:PassportApp.ViewsPassportApp.Views  5   	   "    PassportApp.Views.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   1localclr-namespace:PassportApp.Views     5   	   -   5          � Title$  Passports manager��5   	    � SizeToContent$ WidthAndHeight=�5	   	    � 	Resources 5       �� 
TargetType �� Property �� Value��%L  '��       '�� K     '�� �     �� 5   
   " ��6   .�5      ��  � ��$ 10, 2q�6(   5      �� 5   
   " ��6   .�5      ��  � ��$ Center=�69   5      �� 5   
   " ��6   .�5      ��  � ��$ 10��6&   �� 5       � ��$ 10��6'   �� 5       �@J�Opacity$ 0Q�6(   5      5      .��5      ]� $��20q�6   .+�5   
   ��  �� Orientation$ Vertical=�6   $��Right=�5      .+�5      ]� .+�5      �� $��
First name��6   $��80��5       $
��Left=�5!      �� 5"      $
��Left=�6   �� 5#      $	��100��5$      ��5#      ��  �� Path$  SelectedPassport.FirstName�5$      5%      ]� 5'      .+�5(      �� $��	Last name��6   $��80��5)      $
��Left=�5*      �� 5+      $
��Left=�6   �� 5,      $	��100��5-      ��5,      �� $ SelectedPassport.LastName�5-      5.      ]� 50      .+�51      �� $��Date of birth��6   $��80��52      $
��Left=�53      �� 54      $
��Left=�6   �� 55      $	��100��56      ��55      �� $" SelectedPassport.DateOfBirth�56      57      ]� 59      .+�5:      �� $��Height��6   $��80��5;      $
��Left=�5<      �� 5=      $
��Left=�6   �� 5>      $	��100��5?      ��5>      �� $ SelectedPassport.Height�5?      5@      ]� 5B      .+�5C      �� $��Country��6   $��80��5D      $
��Left=�5E      �� 5F      $
��Left=�6   �� 5G      $	��100��5H      ��5G      �� $ SelectedPassport.Country�5H      5I      5K      �� 5M   
   $��Right=�6   �� 5O   
   $ Vertical=�6   $	��180��5P      $	��180��5Q      $
��Left=�5R      .+�5T      ]� .+�5U      �� $��
First name��6   �� 5V      -   5         ��Name$ FirstNameTextBox��5V      ��5W      �� $$ AddPassportViewModel.FirstName�5X      �� 5Z      ]� 5\      .+�5]      �� $��	Last name��6   �� 5^      -   5        $ LastNameTextBox��5^      ��5_      �� $# AddPassportViewModel.LastName�5`      �� 5b      ]� 5d      .+�5e      �� $��Date of birth��6   (  "System.Windows.Controls.DatePicker  5f      -   5        	  Name$	 DateOfBirthDatePicker��5f      
   SelectedDate
 5g      �� $&  AddPassportViewModel.DateOfBirth�5h      �� 5j      ]� 5l      .+�5m      �� $��Height��6   �� 5o      -   5        $ HeightTextBox��5o      ��5p      �� $! AddPassportViewModel.Height� �� TargetNullValue$  ��5q      �� 5s      ]� 5u      .+�5v      �� $��Country��6   �� 5w      -   5        $ CountryTextBox��5w      ��5x      �� $" AddPassportViewModel.Country�5y      �� 5{      �� 5}      $ 
Horizontal=�6   $��Right=�5~      .+�5      �� $	��Add��6   ��5�      �� $ 
AddCommand�5�      5�      �� 5�   
   $
��Left=�6   ]� 5�   
   $
��Left=�6   .+�5�      �� $ 
Horizontal=�6   $��Right=�5�      $��Bottom=�5�      .+�5�      �� $��Clear��6   ��5�      �� $ ClearCommand��� 5�      �� 5�      $��Remove��6   ��5�      �� $ RemoveCommand�5�      �� 5�      $��Bottom=�6   &   System.Windows.Controls.DataGrid  5�      -   5          Name$ PassportsDataGrid��5�         AutoGenerateColumns$ False��5�         CanUserAddRows$ False��5�         MaxColumnWidth$	 200Q�5�      $	��185��5�      $	��Top=�5�         SelectionMode$ Single=�5�      ��5�      �� $ 	Passports� �� SelectedItem 5�      �� $ SelectedPassport�   Columns 5�      0  *System.Windows.Controls.DataGridTextColumn  5�      ,  &System.Windows.Controls.DataGridColumn   Header$ Id��6)      Width5  /System.Windows.Controls.DataGridLengthConverter$
 Auto 5�   )   1  +System.Windows.Controls.DataGridBoundColumn   Binding 5�   )   �� $ Id�  5�      $ 
First name��6)   $
 Auto 5�   )    5�   )   �� $ 	FirstName�  5�      $ 	Last name��6)   $
 Auto 5�   )    5�   )   �� $ LastName�5�      5�      5�      5�      