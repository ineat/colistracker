using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UnitTests.Configuration;
using Consumer.API.Interfaces;
using FluentAssertions;
using MarketConsumer.API.Enums;
using MarketConsumer.API.Models;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Recipe.API.Infrastructure;
using Recipe.API.Models;
using Recipe.UnitTests.Configuration;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Xunit;


namespace UnitTests.Integration
{
    public class HistoryEventProducerIntegrationTest : TrackerIntegrationTest
    {
        [Fact]
        public async Task Execute_doit_ajouter_les_traductions_manquantes_si_ok()
        {
            // arrange
            string marketId = "France";
            MarketMessage marketMessage = new MarketMessage { Action = ActionType.UpdateLanguages.ToString(), Languages = new List<string> { "fr", "en" }, MarketId = marketId };
            using (TestServer = new TestServer(HostConfiguration.OfflineValidHostBuilder()))
            {
                ResetDatabase(Dataset.AddTranslations);
                Dictionary<string, IAction<MarketMessage>> iActions = TestServer.Host.Services.CreateScope().ServiceProvider.GetServices<IAction<MarketMessage>>().ToDictionary(action => action.Action);

                // act
                await iActions[ActionType.UpdateLanguages.ToString()].Execute(marketMessage);

                // assert
                RecipeContext recipeContext = TestServer.Host.Services.CreateScope().ServiceProvider.GetService<RecipeContext>();
                IEnumerable<API.Models.Recipe> recipes = recipeContext.Recipes.Where(o => o.MarketId == marketId).ToList();
                IEnumerable<Family> families = recipeContext.Families.Where(o => o.MarketId == marketId).ToList();
                recipes.First().Translations.Select(o => o.LanguageIsoCode).Distinct().Should().Contain(marketMessage.Languages!);
                recipes.First().Diagrams.First().Translations.Select(o => o.LanguageIsoCode).Distinct().Should().Contain(marketMessage.Languages!);
                recipes.First().Formulas.First().Translations.Select(o => o.LanguageIsoCode).Distinct().Should().Contain(marketMessage.Languages!);
                recipes.First().Tips.First().Translations.Select(o => o.LanguageIsoCode).Distinct().Should().Contain(marketMessage.Languages!);
                families.First().Translations.Select(o => o.LanguageIsoCode).Distinct().Should().Contain(marketMessage.Languages!);
            }
        }
    }
}
