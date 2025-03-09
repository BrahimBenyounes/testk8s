using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Hosting;

var builder = WebApplication.CreateBuilder(args);

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}

app.UseHttpsRedirection();

app.UseRouting();

app.UseEndpoints(endpoints =>
{
    endpoints.MapGet("/", async context =>
    {
        await context.Response.WriteAsync(@"
            <!DOCTYPE html>
            <html lang=""en"">
            <head>
                <meta charset=""UTF-8"">
                <meta name=""viewport"" content=""width=device-width, initial-scale=1.0"">
                <title>Hello, World!</title>
                <style>
                    .hello-world {
                        font-size: 3em; /* Increase font size */
                        font-weight: bold; /* Optionally increase font weight */
                        color: blue; /* Optionally change text color */
                        text-align: center; /* Center text */
                        margin-top: 20vh; /* Adjust vertical margin */
                    }
                </style>
            </head>
            <body>
                <div class=""hello-world"">Hello, World!</div>
            </body>
            </html>
        ");
    });
});

app.Run();
