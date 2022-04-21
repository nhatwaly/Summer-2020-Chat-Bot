Project 1 - Chat box
Nhat Pham

The bot will connect to #NhatPhamChatBox under the name NhatPhamChatBot on Freenode IRC
type "syntax" for the list of functions and syntaxes.

OpenWeather API : enter the location and get the weather at that location.
Syntax:
weather <city> or weather <zipcode> (for city or zipcode), for example: weather richardson or weather 75080
weather <city> <country code> (for city and country), for example: weather richardson us
weather <city> <state code> <country code> (for city, state and country - only available for the United States ), for example: weather richardson tx us
State code and country code are their corresponding 2 letters abbreviation. For city name with 2 words or above, insert \"%20\" instead of space, for example: los%20angeles for Los Angeles.
-This API has 3 overloads corresponding to the 3 input options from the OpenWeather API
Output: current, high and low temperature, longitude and latitude for the location


GeoDataSource API: enter the longitude and latitude and get the nearest city/location
Syntax:
finder <longitude> <latitude>, for example: finder -95.37 29.76
Output: City or location nearest to the provided longitude and latitude along with region and country.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

I approached this project differently from the normal order. I chose to work on the FreenodeIRC first because I wanted to have an environment to test the provided codes from the instructions and on the internet before researching the APIs. I got stuck free long at this stage because the way login account, nickname and chatroom works on Freenode is confusing. I tried to make a chatroom call “NhatPhamChatBox” but instead I kept messaging to an account with the nickname “NhatPhamChatBox” instead of a chatroom with the same name because I forgot the # in front of the name, it took me more than 1 hour to figure out. After about 1 and a half hours, the chat room is ready. Next, I needed to get the OpenWeather API working.
After diving through the codes for another hour, I successfully got the bot to connect to Freenode and respond to my “Hello”. Then, I read everything I could for the next day about API and how to apply them to the Java project 

The idea of the second API, GeoDataSource, came when I was testing the weather API. I was able to get it working, returning the location’s city name and country name along with the temperatures. Converting the temperature into F degree was not a hard task. However, when working on the overloads for the input, I realized that I had no choice over the output for generic location’s name like “Washington”. The API picks the one on top of their database. I was able to call for the data from Washington-Illinois, Washington-Georgia and Washington DC but I couldn’t tell them apart from the output. The same thing with similar cities from different countries like Sydney from Australia and Sydney from Canada. 
I tried to figure out what other data I can mine from the API and found out that the only way to tell them apart is the longitude and latitude. I wondered if there’s any way to turn the longitude and latitude into the location and came across this idea, looked up on the API site and found this API. That’s why I chose the GeoDataSource API that is capable of doing what I want as my second. It’s essentially doing the reverse of the weather API, getting the longitude and latitude and output the exact location or city , state/province and country. 
From this project, I learned how computer software interacts with online softwares. That’s one thing I always wanted to get into. Moreover, I learned what an API is and how to use them. They are really one of the most useful things you can learn as a programmer. I also got to polish my Java skill a bit more and learned how to deal with null objects.
