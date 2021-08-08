Feature: Test online calculator scenarios - Advanced Arithmetic

  Scenario Outline: Test arithmetic operation for several numbers <values> and operators <operators>
    Given Open online calculator application
    When I perform operations on several values and press CE button
      | values    | <values>    |
      | operators | <operators> |
    Then I should be able to see
      | expected | <expected> |

    Examples: 
      #Same Operator
      | values                    | operators | expected     |
      | 1,4.5,999999999,0         | +         | 1e+9         |
      | 1,4.5,999999999,0         | -         | -1e+9        |
      | 1,4.5,999999999           | *         | 4.5e+9       |
      | 1,4.5,999999999           | /         | 2.222222e-10 |
      #Mixed Operator (n number of values, n-1 number of operators)
      | 23,12.05,3#,6.99#,0.56789 | +,-,*,/   |  -1.87616528 |
