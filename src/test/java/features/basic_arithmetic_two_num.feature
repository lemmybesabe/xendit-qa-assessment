Feature: Test online calculator scenarios - Basic Arithmetic

  Scenario Outline: Test arithmetic operation (<operator>) for numbers <value1> and <value2>
    Given Open online calculator application
    When I perform operation on two values and press CE button
      | value1   | <value1>   |
      | value2   | <value2>   |
      | operator | <operator> |
    Then I should be able to see
      | expected | <expected> |

    Examples: 
      | value1       | value2       | operator | expected     |
      #Addition
      |            1 |            9 | +        |           10 |
      |            9 |            1 | +        |           10 |
      |        23456 |         7890 | +        | 31 346       |
      | 999999999999 | 111111111111 | +        | 1.111111e+9  |
      |   0.12345678 |   123456.789 | +        | 123 456.912  |
      | 3#           | 2#           | +        |           -5 |
      | wrong?>"     | input#!      | +        |            0 |
      #Subtraction
      |            1 |            9 | -        |           -8 |
      |            9 |            1 | -        |            8 |
      |        23456 |         7890 | -        | 15 566       |
      | 999999999999 | 111111111111 | -        | 888 888 888  |
      |   0.12345678 |   123456.789 | -        | -123 456.666 |
      | 3#           | 2#           | -        |           -1 |
      | wrong?>"     | input#!      | -        |            0 |
      #Multiplication
      |            1 |            9 | *        |            9 |
      |            9 |            1 | *        |            9 |
      |        23456 |         7890 | *        | 185 067 840  |
      | 999999999999 | 111111111111 | *        | 1.111111e+17 |
      |   0.12345678 |   123456.789 | *        | 15 241.5776  |
      | 3#           | 2#           | *        |            6 |
      | wrong?>"     | input#!      | *        |            0 |
      #Division
      |            1 |            9 | /        |   0.11111111 |
      |            9 |            1 | /        |            9 |
      |        23456 |         7890 | /        |   2.97287706 |
      | 999999999999 | 111111111111 | /        |            9 |
      |   0.12345678 |   123456.789 | /        | 9.999999e-7  |
      | 3#           | 2#           | /        |          1.5 |
      | wrong?>"     | input#!      | /        | Error        |
