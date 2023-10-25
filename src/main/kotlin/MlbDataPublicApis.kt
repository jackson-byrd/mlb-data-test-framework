import MlbDataPublicApis.Route.BASE_URI
import MlbDataPublicApis.Route.BROADCASTS_OVER_PERIOD_PATH
import MlbDataPublicApis.Route.CAREER_HITTING_PATH
import MlbDataPublicApis.Route.CAREER_PITCHING_PATH
import MlbDataPublicApis.Route.CURRENT_INJURIES_PATH
import MlbDataPublicApis.Route.FORTY_MAN_ROSTER_PATH
import MlbDataPublicApis.Route.HITTING_LEADERS_PATH
import MlbDataPublicApis.Route.INFO_PER_GAME_PATH
import MlbDataPublicApis.Route.LEAGUE_HITTING_PATH
import MlbDataPublicApis.Route.LEAGUE_PITCHING_PATH
import MlbDataPublicApis.Route.PITCHING_LEADERS_PATH
import MlbDataPublicApis.Route.PLAYER_INFO_PATH
import MlbDataPublicApis.Route.PLAYER_SEARCH_PATH
import MlbDataPublicApis.Route.PROJECTED_HITTING_PATH
import MlbDataPublicApis.Route.PROJECTED_PITCHING_PATH
import MlbDataPublicApis.Route.ROSTER_BY_SEASONS_PATH
import MlbDataPublicApis.Route.SEASON_HITTING_PATH
import MlbDataPublicApis.Route.SEASON_PITCHING_PATH
import MlbDataPublicApis.Route.TEAMS_BY_SEASON_PATH
import MlbDataPublicApis.Route.TEAMS_PLAYED_FOR_PATH
import MlbDataPublicApis.Route.TRANSACTIONS_OVER_PERIOD_PATH
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.apache.http.HttpStatus

object MlbDataPublicApis {

    object Route {
        const val BASE_URI = "http://lookup-service-prod.mlb.com"
        private const val PREFIX = "/json/named."

        // Player Data
        const val PLAYER_SEARCH_PATH = "${PREFIX}search_player_all.bam"
        const val PLAYER_INFO_PATH = "${PREFIX}player_info.bam"
        const val TEAMS_PLAYED_FOR_PATH = "player_teams.bam"

        // Stats Data
        const val SEASON_HITTING_PATH = "${PREFIX}sport_hitting_tm.bam"
        const val SEASON_PITCHING_PATH = "${PREFIX}sport_pitching_tm.bam"
        const val CAREER_HITTING_PATH = "${PREFIX}sport_career_hitting.bam"
        const val CAREER_PITCHING_PATH = "${PREFIX}sport_career_pitching.bam"
        const val LEAGUE_HITTING_PATH = "${PREFIX}sport_career_hitting_lg.bam"
        const val LEAGUE_PITCHING_PATH = "${PREFIX}sport_career_pitching_lg"
        const val PROJECTED_HITTING_PATH = "${PREFIX}proj_pecota_pitching.bam"
        const val PROJECTED_PITCHING_PATH = "${PREFIX}proj_pecota_batting.bam"

        // Team Data
        const val TEAMS_BY_SEASON_PATH = "${PREFIX}team_all_season.bam"
        const val FORTY_MAN_ROSTER_PATH = "${PREFIX}roster_40.bam"
        const val ROSTER_BY_SEASONS_PATH = "${PREFIX}roster_team_alltime.bam"

        // Game Data
        const val INFO_PER_GAME_PATH = "${PREFIX}org_game_type_date_info.bam"

        // Reports
        const val TRANSACTIONS_OVER_PERIOD_PATH = "${PREFIX}transaction_all.bam"
        const val BROADCASTS_OVER_PERIOD_PATH = "${PREFIX}mlb_broadcast_info.bam"
        const val HITTING_LEADERS_PATH = "${PREFIX}leader_hitting_repeater.bam"
        const val PITCHING_LEADERS_PATH = "${PREFIX}leader_pitching_repeater.bam"

        // Fantasy
        const val CURRENT_INJURIES_PATH = "/fantasylookup/json${PREFIX}/wsfb_news_injury.bam"
    }

    /**
     * Player Search
     *
     * Search for active and historic/inactive players by name.
     *
     * The active_sw parameter should be set depending on whether you want to search for active or inactive players.
     * You can omit this parameter, though you will notice a slower response time as the search is done across all (active and inactive) players.
     *
     * @param active_sw (optional) Set to 'Y' to search active players, and 'N' to search inactive/historic players. Example: 'Y'
     * @param name_part (required) The player name to search for.  Example: 'cespedes%25'
     *
     * @return [Response]
     */
    fun callGetPlayerSearch(
        active_sw: String,
        name_part: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("active_sw", active_sw)
            .queryParam("name_part", name_part)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(PLAYER_SEARCH_PATH)
    }

    /**
     * Player Info
     *
     * Retrieve general information on a player. This includes name variants, education information, country of origin and attributes like height, weight and age.
     *
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetPlayerInfo(
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(PLAYER_INFO_PATH)
    }

    /**
     * Player Teams
     *
     * Retrieve the teams a player has played for over the course of a season, or their career
     *
     * @param season (optional) Ex: '2014'
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetPlayerTeams(
        season: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("season", season)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(TEAMS_PLAYED_FOR_PATH)
    }

    /**
     * Season Hitting Stats
     *
     * Retrieve a players hitting stats for a given season
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param season (required) Example: '2017'
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetSeasonHittingStats(
        game_type: String,
        season: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("season", season)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(SEASON_HITTING_PATH)
    }

    /**
     * Season Pitching Stats
     *
     * Retrieve a players pitching stats for a given season.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param season (required) Example: '2017'
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetSeasonPitchingStats(
        game_type: String,
        season: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("season", season)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(SEASON_PITCHING_PATH)
    }

    /**
     * Career Hitting Stats
     *
     * Retrieve a players career hitting stats for a given game type.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetCareerHittingStats(
        game_type: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(CAREER_HITTING_PATH)
    }

    /**
     * Career Pitching Stats
     *
     * Retrieve a players career pitching stats for a given game type.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetCareerPitchingStats(
        game_type: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(CAREER_PITCHING_PATH)
    }

    /**
     * League Hitting Stats
     *
     * Retrieve a players career hitting stats for a given game type split by the league.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetLeagueHittingStats(
        game_type: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(LEAGUE_HITTING_PATH)
    }

    /**
     * League Pitching Stats
     *
     * Retrieve a players career pitching stats for a given game type split by the league.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetLeaguePitchingStats(
        game_type: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(LEAGUE_PITCHING_PATH)
    }

    /**
     * Projected Hitting Stats
     *
     * Retrieve a players projected hitting stats for a given season.
     * Omitting the season parameter will return the actual stats for the players earliest major league season.
     *
     * @param season (optional) Example: '2017'
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetProjectedHittingStats(
        season: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("season", season)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(PROJECTED_HITTING_PATH)
    }

    /**
     * Projected Pitching Stats
     *
     * Retrieve a players projected pitching stats for a given season.
     * Omitting the season parameter will return the actual stats for the players earliest major league season.
     *
     * @param season (optional) Example: '2017'
     * @param player_id (required) Example: '493316'
     *
     * @return [Response]
     */
    fun callGetProjectedPitchingStats(
        season: String,
        player_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("season", season)
            .queryParam("player_id", player_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(PROJECTED_PITCHING_PATH)
    }

    /**
     * Retrieve a list of major league teams that were active during a given season.
     *
     * If all_star_sw is set to 'Y', you will instead receive data on the all star teams for that season.
     *
     * You can sort using the sort_order parameter. Ex: Sort in ascending order by the name field using sort_by='name_asc'
     *
     * @param all_star_sw (optional) Set to ‘Y’ for all star data, and ‘N’ for regular season.
     * @param sort_order (optional) Field to sort results by. Example: name_asc
     * @param season (required) Example: '2017'
     *
     * @return [Response]
     */
    fun callGetTeamsBySeason(
        all_star_sw: String,
        sort_order: String,
        season: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("all_star_sw", all_star_sw)
            .queryParam("sort_order", sort_order)
            .queryParam("season", season)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(TEAMS_BY_SEASON_PATH)
    }

    /**
     * 40-Man Roster
     *
     * Retrieve a team’s 40-man roster.
     * This endpoint is best used alongside col_in/col_ex to prune response data. Without, it returns entire player objects.
     *
     * @param team_id (required) Example: '121'
     *
     * @return [Response]
     */
    fun callGetFortyManRoster(
        team_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("team_id", team_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(FORTY_MAN_ROSTER_PATH)
    }

    /**
     * Roster By Seasons
     *
     * Retrieve a teams roster between a given start and end season.
     * Enter the same season for start_season and end_season to get the roster for that single season.
     * This endpoint is best used alongside col_in/col_ex to prune response data. Without, it returns entire player objects.
     *
     * @param start_season (required) Example: '2016'
     * @param end_season (required) Example: '2017'
     * @param team_id (required) Example: '121'
     *
     * @return [Response]
     */
    fun callGetRosterBySeasons(
        start_season: String,
        end_season: String,
        team_id: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("start_season", start_season)
            .queryParam("end_season", end_season)
            .queryParam("team_id", team_id)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(ROSTER_BY_SEASONS_PATH)
    }

    /**
     * Game Type Info
     *
     * Retrieve a list of one or numerous game types.
     * For example, if you wanted to know when the National League Championship Series was played, this endpoint could tell you that.
     *
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param season (required) Example: '2017'
     *
     * @return [Response]
     */
    fun callGetInfoPerGame(
        game_type: String,
        season: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("game_type", game_type)
            .queryParam("season", season)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(INFO_PER_GAME_PATH)
    }

    /**
     * Transactions
     *
     * Retrieve all transactions between a given period.
     *
     * @param start_date (required) Start date of time period. Must be in YYYYMMDD format Example: '20171201'
     * @param end_date (required) End date of time period. Must be in YYYYMMDD format Example: '20171231'
     *
     * @return [Response]
     */
    fun callGetTransactionsOverPeriod(
        start_date: String,
        end_date: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("start_date", start_date)
            .queryParam("end_date", end_date)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(TRANSACTIONS_OVER_PERIOD_PATH)
    }

    /**
     * Broadcast Info
     *
     * Retrieve information on broadcasts over a given period.
     *
     * Although you can omit the home_away parameter to retrieve both home and away game data, one will include the other.
     * For example, a New York Mets home game result will include data for the visiting team.
     *
     * @param sort_by (optional) Field to sort results by.  Example: 'game_time_et_asc'
     * @param home_away (optional) ‘H’ for home games, ‘A’ for away games. Omit for both.
     * @param start_date (optional) Start date of time period. Must be in YYYYMMDD format Example: '20171201'
     * @param end_date (optional) End date of time period. Must be in YYYYMMDD format Example: '20171231'
     * @param season (required)  Example: '2017'
     *
     * @return [Response]
     */
    fun callGetBroadcastsOverPeriod(
        sort_by: String,
        home_away: String,
        start_date: String,
        end_date: String,
        season: String,
        expectedStatus: Int
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("sort_by", sort_by)
            .queryParam("home_away", home_away)
            .queryParam("start_date", start_date)
            .queryParam("end_date", end_date)
            .queryParam("season", season)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(BROADCASTS_OVER_PERIOD_PATH)
    }

    /**
     * Hitting Leaders
     *
     * Retrieve n leaders for a given hitting statistic.
     *
     * @param results (required) The number of results to return. Example: 5
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param season (required)  Example: '2017'
     * @param sort_column (required) The statistic you want leaders for Example: 'ab'
     * @param leader_hitting_repeater (optional) Example: 'ab'
     *
     * @return [Response]
     */
    fun callGetHittingLeaders(
        results: Int,
        game_type: String,
        season: String,
        sort_column: String,
        leader_hitting_repeater: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("results", results)
            .queryParam("game_type", game_type)
            .queryParam("season", season)
            .queryParam("sort_column", sort_column)
            .queryParam("leader_hitting_repeater.col_in", leader_hitting_repeater)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(HITTING_LEADERS_PATH)
    }

    /**
     * Pitching Leaders
     *
     * Retrieve n leaders for a given hitting statistic.
     *
     * @param results (required) The number of results to return. Example: 5
     * @param game_type (required) The type of games you want career stats for.
     *                             'R' - Regular Season
     *                             'S' - Spring Training
     *                             'E' - Exhibition
     *                             'A' - All Star Game
     *                             'D' - Division Series
     *                             'F' - First Round (Wild Card)
     *                             'L' - League Championship
     *                             'W' - World Series
     * @param season (required)  Example: '2017'
     * @param sort_column (required) The statistic you want leaders for Example: 'era'
     * @param leader_pitching_repeater (optional) Example: 'era'
     *
     * @return [Response]
     */
    fun callGetPitchingLeaders(
        results: Int,
        game_type: String,
        season: String,
        sort_column: String,
        leader_pitching_repeater: String,
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .queryParam("results", results)
            .queryParam("game_type", game_type)
            .queryParam("season", season)
            .queryParam("sort_column", sort_column)
            .queryParam("leader_pitching_repeater.col_in", leader_pitching_repeater)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(PITCHING_LEADERS_PATH)
    }

    /**
     * Injuries
     *
     * Retrieve all players which are currently injured.
     *
     * @return [Response]
     */
    fun callGetCurrentInjuries(
        expectedStatus: Int = HttpStatus.SC_OK
    ): Response {
        return given()
            .baseUri(BASE_URI)
            .expect()
            .statusCode(expectedStatus)
            .`when`()
            .get(CURRENT_INJURIES_PATH)
    }
}