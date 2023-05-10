Feature: Testing weather API site

  Scenario Outline: Get weather details by city name: <city>
    Given Request specification with the city name "<city>"
    When Send a GET request
    Then Response should have status code 200
    And Response should have a valid JSON schema
    And Response should have the expected city name "<city>"

    Examples:
      | city      |
      | London    |
      | New York  |
      | Warsaw    |

  Scenario Outline: Get weather details by city ID: <id>
    Given Request specification with the city ID <id>
    When Send a GET request
    Then Response should have status code 200
    And Response should have a valid JSON schema
    And Response should have the expected city name "<city>"

    Examples:
      | id      | city     |
      | 2643743 | London   |
      | 5128581 | New York |
      | 756135  | Warsaw   |


